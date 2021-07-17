package com.chandranedu.api.cart.controller;

import com.chandranedu.api.cart.service.CartService;
import com.chandranedu.api.cart.dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/api/carts")
    public ResponseEntity<CartDTO> createNewCart(@RequestBody CartDTO cartDTO) {

        final CartDTO cartDTOResponse = cartService.saveCart(cartDTO);
        return new ResponseEntity<>(cartDTOResponse, HttpStatus.CREATED);
    }
}

