package com.chandranedu.cart.service;

import com.chandranedu.cart.beans.Cart;
import com.chandranedu.cart.beans.CartEntry;
import com.chandranedu.cart.dto.AddressDTO;
import com.chandranedu.cart.dto.CartDTO;
import com.chandranedu.cart.dto.CartEntryDTO;
import com.chandranedu.cart.exception.CartEntryException;
import com.chandranedu.cart.exception.CartNotFoundException;
import com.chandranedu.cart.mapper.CartEntryMapper;
import com.chandranedu.cart.mapper.CartMapper;
import com.chandranedu.cart.repository.CartEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.chandranedu.cart.mapper.AddressMapper.mapToAddressDTO;
import static com.chandranedu.cart.mapper.CartEntryMapper.mapToCartEntry;
import static com.chandranedu.cart.mapper.CartEntryMapper.mapToCartEntryDTO;
import static java.util.stream.Collectors.toList;

@Service
public class CartEntryService {

    private final CartService cartService;
    private final CartEntryRepository cartEntryRepository;

    @Autowired
    public CartEntryService(final CartService cartService,
                            final CartEntryRepository cartEntryRepository) {

        this.cartEntryRepository = cartEntryRepository;
        this.cartService = cartService;
    }

    public CartDTO addItemToCart(String code,
                                 CartEntryDTO cartEntryDTO)
            throws CartEntryException, CartNotFoundException {

        final Cart cart = getCart(code);
        itemAlreadyInCart(cartEntryDTO.getCartEntryCode())
                .orElseThrow(this::cartEntryException);

        return mapToCartDTO(cart);
    }

    public CartEntryDTO saveCartEntry(CartEntryDTO cartEntryDTO) {

        final CartEntry cartEntry = mapToCartEntry(cartEntryDTO);
        final CartEntry saveCartEntry = cartEntryRepository.save(cartEntry);
        return mapToCartEntryDTO(saveCartEntry);
    }

    private List<CartEntryDTO> getCartsByCartCode(String code) {

        final List<CartEntry> cartEntryByCode = cartEntryRepository.findAll();
        return cartEntryByCode.stream()
                .filter(Objects::nonNull)
                .map(CartMapper::mapToCartEntryDTO)
                .collect(toList());

    }


    private CartEntryDTO getCartsByCartCodeAndEntries(String code) throws CartEntryException {

        final CartEntry cartEntryByCode = itemAlreadyInCart(code)
                .orElseThrow(this::cartEntryException);
        return CartMapper.mapToCartEntryDTO(cartEntryByCode);
    }


    private CartEntryException cartEntryException() {
        return new CartEntryException("Entry Already Present");
    }

    private CartNotFoundException cartNotFoundException() {
        return new CartNotFoundException("Cart Not Found");
    }

    private Cart getCart(String code) throws CartNotFoundException {
        return cartService.getCartByCode(code)
                .orElseThrow(this::cartNotFoundException);
    }


    private static CartEntry getCartEntry(CartEntryDTO entryDTO,
                                          Cart cart) {

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCart(cart);
        cartEntry.setCode(entryDTO.getCartEntryCode());
        cartEntry.setQuantity(entryDTO.getQuantity());
        cartEntry.setTotal(entryDTO.getTotal().multiply(BigDecimal.valueOf(entryDTO.getQuantity()))
                .setScale(2, RoundingMode.HALF_UP));
        return cartEntry;
    }

    private CartDTO mapToCartDTO(final Cart cart) {

        final AddressDTO billingAddressDTO = mapToAddressDTO(
                cart.getBillingAddress()
        );
        final AddressDTO shippingAddressDTO = mapToAddressDTO(
                cart.getShippingAddress()
        );
        final List<CartEntryDTO> entriesListDTO = getCartEntryDTOS(
                cart.getEntriesList()
        );

        CartDTO cartDTO = new CartDTO();
        cartDTO.setBillingAddress(billingAddressDTO);
        cartDTO.setShippingAddress(shippingAddressDTO);
        cartDTO.setCode(cart.getCode());
        cartDTO.setDiscounts(cart.getDiscounts());
        cartDTO.setEntriesList(entriesListDTO);
        cartDTO.setTotal(cart.getTotal());
        cartDTO.setTotalTax(cart.getTotal());
        cartDTO.setSubtotal(cart.getSubtotal());
        return cartDTO;
    }

    private List<CartEntryDTO> getCartEntryDTOS(List<CartEntry> cartEntryList) {

        if (CollectionUtils.isEmpty(cartEntryList)) {
            return Collections.emptyList();
        }
        return cartEntryList.
                stream()
                .map(CartEntryMapper::mapToCartEntryDTO)
                .collect(toList());
    }

    public Optional<CartEntry> itemAlreadyInCart(String entryCartCode) {
        return cartEntryRepository.findCartEntryByCode(entryCartCode);
    }
}
