package com.chandranedu.cart.service;

import com.chandranedu.cart.beans.Address;
import com.chandranedu.cart.beans.Cart;
import com.chandranedu.cart.beans.CartEntry;
import com.chandranedu.cart.dto.AddressDTO;
import com.chandranedu.cart.dto.CartDTO;
import com.chandranedu.cart.dto.CartEntryDTO;
import com.chandranedu.cart.exception.CartEntryException;
import com.chandranedu.cart.mapper.CartEntryMapper;
import com.chandranedu.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.chandranedu.cart.mapper.AddressMapper.mapToAddress;
import static com.chandranedu.cart.mapper.AddressMapper.mapToAddressDTO;
import static com.chandranedu.cart.mapper.CartMapper.mapToCart;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartDTO> getCarts() {

        final List<Cart> cartList = cartRepository.findAll();

        return cartList.stream()
                .map(this::mapToCartDTO)
                .collect(Collectors.toList());
    }

    public CartDTO saveCart(CartDTO cartDTO) {

        //TODO cart validation
        final Cart cart = convertToCart(cartDTO);
        final Cart cartDB = cartRepository.save(cart);
        //return the object
        return mapToCartDTO(cartDB);
    }

    private Cart convertToCart(CartDTO cartDTO) {

        final Optional<Address> shippingAddress = mapToAddress(cartDTO.getShippingAddress());
        final Optional<Address> billingAddress = mapToAddress(cartDTO.getBillingAddress());
        final Cart cart = mapToCart(cartDTO);
        cart.setBillingAddress(shippingAddress.orElse(null));
        cart.setBillingAddress(billingAddress.orElse(null));
        return cart;
    }

    public Optional<Cart> getCartByCode(String code) {
        return cartRepository.findCartByCode(code);
    }

//    public Optional<Cart> addItemToCart(CartEntryDTO cartEntryDTO) throws CartEntryException {
//
//        cartEntryService.isItemAlreadyInCart(cartEntryDTO.getCartEntryCode())
//                .orElseThrow(this::cartEntryException);
//
//        final CartEntry cartEntry = cartEntryService.addItemInCart(cartEntryDTO);
//        final Cart cart = convertToCart(cartEntryDTO.getCart());
//
//        List<CartEntry> cartEntryDTOList = cart.getEntriesList();
//        cartEntryDTOList.add(cartEntry);
//        cart.setEntriesList(cartEntryDTOList);
//
//        recalculateCart(cart, cartEntryDTOList);
//        // saveCart(cart);
//        return Optional.of(cart);
//    }


    private CartEntryException cartEntryException() {
        return new CartEntryException("Entry Already Present");
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


    private CartDTO mapToCartDTO(final Cart cart) {

        final Optional<AddressDTO> billingAddressDTO = mapToAddressDTO(
                cart.getBillingAddress()
        );
        final Optional<AddressDTO> shippingAddressDTO = mapToAddressDTO(
                cart.getShippingAddress()
        );
        final List<CartEntryDTO> entriesListDTO = getCartEntryDTOS(
                cart.getEntriesList()
        );

        CartDTO cartDTO = new CartDTO();
        // cartDTO.setBillingAddress(billingAddressDTO.ifPresent(x->x));
        // cartDTO.setShippingAddress(shippingAddressDTO);
        cartDTO.setCode(cart.getCode());
        cartDTO.setDiscounts(cart.getDiscounts());
        cartDTO.setEntriesList(entriesListDTO);
        cartDTO.setTotal(cart.getTotal());
        cartDTO.setTotalTax(cart.getTotal());
        cartDTO.setSubtotal(cart.getSubtotal());
        return cartDTO;
    }

    private List<CartEntryDTO> getCartEntryDTOS(List<CartEntry> entriesList) {

        if (CollectionUtils.isEmpty(entriesList)) {
            return Collections.emptyList();
        }
        return entriesList.stream()
                .map(CartEntryMapper::mapToCartEntryDTO)
                .collect(Collectors.toList());
    }
}
