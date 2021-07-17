package com.chandranedu.api.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {

    private String code;
    private BigDecimal total;
    private BigDecimal totalTax;
    private BigDecimal subtotal;
    private BigDecimal discounts;
    private AddressDTO billingAddress;
    private AddressDTO shippingAddress;
    private List<CartEntryDTO> entriesList=new ArrayList<>();
}
