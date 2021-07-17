package com.chandranedu.cart.controller;

import com.chandranedu.cart.dto.CartDTO;
import com.chandranedu.cart.dto.CartEntryDTO;
import com.chandranedu.cart.exception.CartEntryException;
import com.chandranedu.cart.exception.CartNotFoundException;
import com.chandranedu.cart.service.CartEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CartEntryController {

    private final CartEntryService cartEntryService;

    @Autowired
    public CartEntryController(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @PostMapping("api/v1/carts/{cart-code}/cart-entries")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable final String code,
                                                 @RequestBody final CartEntryDTO cartEntryDTO) throws CartEntryException, CartNotFoundException {

        final CartDTO cartDTO = cartEntryService.addItemToCart(
                code,
                cartEntryDTO
        );
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @GetMapping("api/v1/carts/{cart-code}/cart-entries")
    public ResponseEntity<List<CartEntryDTO>> getCartsByCartCode(@PathVariable("cart-code") String cartCode) {


        return null;
    }

    @GetMapping("api/v1/carts/{cart-code}/cart-entries/{cart-entry-code}")
    public Optional<CartEntryDTO> getCartsByCartCodeAndEntries(@PathVariable("cart-code") String cartCode,
                                                               @PathVariable("cart-entry-code") String cartEntryCode) {


        return null;
    }
}

