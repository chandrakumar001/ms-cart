package com.chandranedu.api.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO {

    private String code;
    private CartBareDTO cart;
}
