package com.chandranedu.cart.mapper;

import com.chandranedu.cart.beans.Address;
import com.chandranedu.cart.beans.Cart;
import com.chandranedu.cart.beans.CartEntry;
import com.chandranedu.cart.dto.CartDTO;
import com.chandranedu.cart.dto.CartEntryDTO;

import java.util.UUID;

public class CartMapper {

    public static CartEntryDTO mapToCartEntryDTO(CartEntry cartEntry) {

        CartEntryDTO cartEntryDTO = new CartEntryDTO();
        cartEntryDTO.setCartEntryCode(cartEntry.getCode());
        cartEntryDTO.setQuantity(cartEntry.getQuantity());
        cartEntryDTO.setTotal(cartEntry.getTotal());
        return cartEntryDTO;
    }

    public static Cart mapToCart(CartDTO cartDTO) {

        Cart cart = new Cart();
        cart.setCode("Cart-" + UUID.randomUUID().toString());
        cart.setDiscounts(cartDTO.getDiscounts());
        cart.setSubtotal(cartDTO.getSubtotal());
        cart.setTotal(cartDTO.getTotal());
        return cart;
    }

}
