package com.chandranedu.api.cart.mapper;

import com.chandranedu.api.cart.dto.CartEntryDTO;
import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.beans.CartEntry;

public class CartEntryMapper {

    public static CartEntry mapToCartEntry(CartEntryDTO cartEntryDTO,
                                           Cart cart) {

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCode(cartEntryDTO.getCartEntryCode());
        cartEntry.setQuantity(cartEntryDTO.getQuantity());
        cartEntry.setTotal(cartEntryDTO.getTotal());
        cartEntry.setCart(cart);
        return cartEntry;
    }

    public static CartEntryDTO mapToCartEntryDTO(CartEntry cartEntry) {

        CartEntryDTO cartEntryDTO = new CartEntryDTO();
        cartEntryDTO.setTotal(cartEntry.getTotal());
        cartEntryDTO.setQuantity(cartEntry.getQuantity());
        cartEntryDTO.setCartEntryCode(cartEntry.getCode());
        return cartEntryDTO;
    }
}
