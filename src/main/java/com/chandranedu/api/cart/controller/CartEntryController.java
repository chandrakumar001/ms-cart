package com.chandranedu.api.cart.controller;

import com.chandranedu.api.cart.dto.CartDTO;
import com.chandranedu.api.cart.dto.CartEntryDTO;
import com.chandranedu.api.cart.service.CartEntryService;
import com.chandranedu.api.exception.CartEntryException;
import com.chandranedu.api.exception.CartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CartEntryController {

    public static final String CART_CODE = "cart-code";
    private final CartEntryService cartEntryService;

    @Autowired
    public CartEntryController(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @PostMapping("api/v1/carts/{cart-code}/cart-entries")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable(name = CART_CODE) final String code,
                                                 @RequestBody final CartEntryDTO cartEntryDTO)
            throws CartEntryException, CartNotFoundException {

        final CartDTO cartDTO = cartEntryService.addItemToCart(
                code,
                cartEntryDTO
        );
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @GetMapping("api/v1/carts/{cart-code}/cart-entries")
    public ResponseEntity<CartDTO> getCartsByCartCode(@PathVariable(name = CART_CODE) final String code)
            throws CartNotFoundException {

        final CartDTO cartDTO = cartEntryService.getCartsByCartCode(
                code
        );
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @GetMapping("api/v1/carts/{cart-code}/cart-entries/{cart-entry-code}")
    public ResponseEntity<CartDTO> getCartsByCartCodeAndEntries(@PathVariable(name = CART_CODE) final String code,
                                                                @PathVariable("cart-entry-code") String cartEntryCode) throws CartEntryException, CartNotFoundException {


        final CartDTO cartDTO = cartEntryService.getCartsByCartCodeAndEntries(
                code
        );
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}

