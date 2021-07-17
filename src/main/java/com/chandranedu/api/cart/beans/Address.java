package com.chandranedu.api.cart.beans;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "cart")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String streetName;
    private String streetNumber;
    private String name;
    private String postalCode;
    private String country;
}
