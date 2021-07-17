package com.chandranedu.api.cart.controller;

import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.dto.CartDTO;
import com.chandranedu.api.cart.dto.CartEntryDTO;
import com.chandranedu.api.cart.service.CartService;
import com.chandranedu.api.exception.CartEntryException;
import com.chandranedu.api.exception.CartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.chandranedu.api.cart.controller.Constant.*;
import static com.chandranedu.api.cart.mapper.CartMapper.mapToCartDTO;

@RestController
public class CartEntryController {

    private final CartService cartService;

    @Autowired
    public CartEntryController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(API_V1_CARTS_CODE_CART_ENTRIES)
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable(name = CART_CODE) final String code,
                                                 @RequestBody final CartEntryDTO cartEntryDTO)
            throws CartNotFoundException, CartEntryException {

        final Cart cart = getAndPrepareCart(code);
        final CartDTO cartDTO = cartService.addItemToCart(
                cartEntryDTO,
                cart
        );
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @GetMapping(API_V1_CARTS_CODE_CART_ENTRIES)
    public ResponseEntity<CartDTO> getCartsByCartCodeAndCartEntries(
            @PathVariable(name = CART_CODE) final String code)
            throws CartNotFoundException {

        final Cart cart = getAndPrepareCart(code);
        final CartDTO cartDTO = mapToCartDTO(cart);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    private Cart getAndPrepareCart(String code) throws CartNotFoundException {

        return cartService.findCartByCode(code)
                .orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));
    }
}