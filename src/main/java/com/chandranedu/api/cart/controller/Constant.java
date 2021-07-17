package com.chandranedu.api.cart.controller;

public class Constant {

    private Constant() {
        throw new IllegalStateException("Constant class");
    }

    public static final String CART_CODE = "cart-code";
    public static final String API_V1_CARTS_CODE_CART_ENTRIES = "api/v1/carts/{cart-code}/cart-entries";
    public static final String API_V1_CARTS_CART_CODE_ENDPOINT = "/api/v1/carts/{cart-code}";
    public static final String API_V1_CARTS_ENDPOINT = "/api/v1/carts";

    public static final String CART_NOT_FOUND = "Cart Not Found";
    public static final String ENTRY_NOT_FOUND = "Entry not found";
    public static final String ENTRY_ALREADY_PRESENT = "Entry Already Present";
}
