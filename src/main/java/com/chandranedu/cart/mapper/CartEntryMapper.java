package com.chandranedu.cart.mapper;

import com.chandranedu.cart.beans.CartEntry;
import com.chandranedu.cart.dto.CartEntryDTO;

public class CartEntryMapper {

    public static CartEntry mapToCartEntry(CartEntryDTO cartEntryDTO) {

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCode(cartEntry.getCode());
        cartEntry.setQuantity(cartEntry.getQuantity());
        cartEntry.setTotal(cartEntryDTO.getTotal());
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
