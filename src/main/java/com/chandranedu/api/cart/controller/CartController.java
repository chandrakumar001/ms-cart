package com.chandranedu.api.cart.controller;

import com.chandranedu.api.cart.dto.CartDTO;
import com.chandranedu.api.cart.service.CartService;
import com.chandranedu.api.exception.CartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.chandranedu.api.cart.controller.Constant.*;

@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(API_V1_CARTS_ENDPOINT)
    public ResponseEntity<CartDTO> createNewCart(@RequestBody CartDTO cartDTO) {

        final CartDTO cartDTOResponse = cartService.saveCart(
                cartDTO
        );
        return new ResponseEntity<>(cartDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping(API_V1_CARTS_ENDPOINT)
    public ResponseEntity<List<CartDTO>> findAllCarts() {

        final List<CartDTO> cartDTOList = cartService.getCarts();
        return new ResponseEntity<>(cartDTOList, HttpStatus.OK);
    }

    @GetMapping(API_V1_CARTS_CART_CODE_ENDPOINT)
    public ResponseEntity<CartDTO> findCartById(@PathVariable(name = CART_CODE) final String code)
            throws CartNotFoundException {

        final CartDTO cartDTOResponse = cartService.getCartByCode(
                code
        );
        return new ResponseEntity<>(cartDTOResponse, HttpStatus.OK);
    }
}

