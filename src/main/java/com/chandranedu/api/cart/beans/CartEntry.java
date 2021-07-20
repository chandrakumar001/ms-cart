package com.chandranedu.api.cart.beans;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "cart_entry", schema = "cart")
public class CartEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_entry_seq")
    @SequenceGenerator(schema = "cart", name = "cart_entry_seq", sequenceName = "cart_entry_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String code;
    private BigDecimal total;
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
