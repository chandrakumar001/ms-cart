package com.chandranedu.api.cart.beans;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Table(name = "cart", schema = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    @SequenceGenerator(schema = "cart", name = "cart_seq", sequenceName = "cart_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String code;
    private BigDecimal total;
    private BigDecimal totalTax;
    private BigDecimal subtotal;
    private BigDecimal discounts;
    @OneToOne(fetch = LAZY, cascade = {PERSIST, MERGE})
    @JoinColumn(name = "address_billing_id")
    private Address billingAddress;
    @OneToOne(fetch = LAZY, cascade = {PERSIST, MERGE})
    @JoinColumn(name = "address_shipping_id")
    private Address shippingAddress;
    @OneToMany(fetch = EAGER, cascade = {PERSIST, MERGE}, mappedBy = "cart")
    private List<CartEntry> entriesList;
}
