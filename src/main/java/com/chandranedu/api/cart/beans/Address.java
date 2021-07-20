package com.chandranedu.api.cart.beans;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "cart")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(schema = "cart", name = "address_seq", sequenceName = "address_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String streetName;
    private String streetNumber;
    private String name;
    private String postalCode;
    private String country;
}
