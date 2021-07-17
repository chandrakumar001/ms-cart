package com.chandranedu.api.cart.dto;

import lombok.Data;

@Data
public class AddressBareDTO {

    private String streetName;
    private String streetNumber;
    private String name;
    private String postalCode;
    private String country;
}
