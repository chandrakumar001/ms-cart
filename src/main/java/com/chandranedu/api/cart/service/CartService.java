package com.chandranedu.api.cart.service;


import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.beans.CartEntry;
import com.chandranedu.api.cart.dto.CartBareDTO;
import com.chandranedu.api.cart.dto.CartDTO;
import com.chandranedu.api.cart.dto.CartEntryDTO;
import com.chandranedu.api.cart.mapper.CartMapper;
import com.chandranedu.api.cart.repository.CartRepository;
import com.chandranedu.api.exception.CartEntryException;
import com.chandranedu.api.exception.CartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.chandranedu.api.cart.controller.Constant.CART_NOT_FOUND;
import static com.chandranedu.api.cart.controller.Constant.ENTRY_ALREADY_PRESENT;
import static com.chandranedu.api.cart.mapper.CartMapper.mapToCart;
import static com.chandranedu.api.cart.mapper.CartMapper.mapToCartDTO;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartEntryService cartEntryService;

    @Autowired
    public CartService(final CartEntryService cartEntryService,
                       final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.cartEntryService = cartEntryService;
    }

    public List<CartDTO> getCarts() {

        final List<Cart> cartList = cartRepository.findAll();
        return cartList.stream()
                .filter(Objects::nonNull)
                .map(CartMapper::mapToCartDTO)
                .collect(Collectors.toList());
    }

    public CartDTO saveCart(final CartBareDTO cartBareDTO) {

        //TODO cart validation
        final Cart cart = mapToCart(cartBareDTO);
        final Cart cartDB = cartRepository.save(cart);
        return mapToCartDTO(cartDB);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public CartDTO getCartByCode(String code) throws CartNotFoundException {

        final Cart cart = findCartByCode(code)
                .orElseThrow(this::cartNotFoundException);
        return mapToCartDTO(cart);
    }

    public Optional<Cart> findCartByCode(String code) {
        return cartRepository.findCartByCode(code);
    }

    public CartDTO addItemToCart(final CartEntryDTO cartEntryDTO,
                                 final Cart cart)
            throws CartEntryException {

        final Optional<CartEntry> cartEntryOptional = cartEntryService.getCartEntry(
                cartEntryDTO.getCartEntryCode()
        );
        if (cartEntryOptional.isPresent()) {
            throw new CartEntryException(ENTRY_ALREADY_PRESENT);
        }

        final CartEntry cartEntry = cartEntryService.addItemInCart(
                cartEntryDTO,
                cart
        );
        List<CartEntry> cartEntryDTOList = cart.getEntriesList();
        cartEntryDTOList.add(cartEntry);
        cart.setEntriesList(cartEntryDTOList);

        recalculateCart(cart, cartEntryDTOList);
        saveCart(cart);
        return mapToCartDTO(cart);
    }

    public void recalculateCart(Cart cart, List<CartEntry> entries) {

        BigDecimal total = entries.stream()
                .map(CartEntry::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        total = total.setScale(2, RoundingMode.HALF_UP);

        cart.setTotal(total);
        cart.setSubtotal(total);
        cart.setDiscounts(new BigDecimal(0));

        BigDecimal tax = total.multiply(new BigDecimal(0.19))
                .setScale(2, RoundingMode.HALF_UP);
        cart.setTotalTax(tax);
    }

    private CartNotFoundException cartNotFoundException() {
        return new CartNotFoundException(CART_NOT_FOUND);
    }
}
