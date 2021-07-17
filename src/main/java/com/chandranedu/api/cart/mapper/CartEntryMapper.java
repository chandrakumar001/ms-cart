package com.chandranedu.api.cart.mapper;

import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.beans.CartEntry;
import com.chandranedu.api.cart.dto.CartEntryDTO;

public class CartEntryMapper {

    private CartEntryMapper() {
        throw new IllegalStateException("CartEntryMapper class");
    }

    public static CartEntry mapToCartEntry(CartEntryDTO cartEntryDTO,
                                           Cart cart) {

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCode(cartEntryDTO.getCartEntryCode());
        cartEntry.setQuantity(cartEntryDTO.getQuantity());
        cartEntry.setTotal(cartEntryDTO.getPrice());
        cartEntry.setCart(cart);
        return cartEntry;
    }

    public static CartEntry mapToCartEntry(CartEntryDTO cartEntryDTO) {

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCode(cartEntryDTO.getCartEntryCode());
        cartEntry.setQuantity(cartEntryDTO.getQuantity());
        cartEntry.setTotal(cartEntryDTO.getPrice());
        return cartEntry;
    }

    public static CartEntryDTO mapToCartEntryDTO(CartEntry cartEntry) {

        CartEntryDTO cartEntryDTO = new CartEntryDTO();
        cartEntryDTO.setPrice(cartEntry.getTotal());
        cartEntryDTO.setQuantity(cartEntry.getQuantity());
        cartEntryDTO.setCartEntryCode(cartEntry.getCode());
        return cartEntryDTO;
    }
}
