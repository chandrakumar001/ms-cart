package com.chandranedu.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartEntryDTO {

    private String cartEntryCode;
    private Long quantity;
    private BigDecimal total;
}
