package com.chandranedu.api.cart.mapper;

import com.chandranedu.api.cart.beans.Address;
import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.beans.CartEntry;
import com.chandranedu.api.cart.dto.*;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.chandranedu.api.cart.mapper.AddressMapper.mapToAddress;
import static com.chandranedu.api.cart.mapper.AddressMapper.mapToAddressBareDTO;
import static com.chandranedu.api.cart.mapper.CartEntryMapper.mapToCartEntry;

public class CartMapper {

    private CartMapper() {
        throw new IllegalStateException("CartMapper class");
    }

    public static CartDTO mapToCartDTO(final Cart cart) {

        if (Objects.isNull(cart)) {
            return null;
        }
        final Optional<AddressBareDTO> billingAddressDTO = mapToAddressBareDTO(
                cart.getBillingAddress()
        );
        final Optional<AddressBareDTO> shippingAddressDTO = mapToAddressBareDTO(
                cart.getShippingAddress()
        );
        final List<CartEntryDTO> entriesListDTO = getCartEntryDTO(
                cart.getEntriesList()
        );

        CartBareDTO cartBareDTO = new CartBareDTO();
        cartBareDTO.setBillingAddress(billingAddressDTO.orElse(null));
        cartBareDTO.setShippingAddress(shippingAddressDTO.orElse(null));
        cartBareDTO.setDiscounts(cart.getDiscounts());
        cartBareDTO.setEntriesList(entriesListDTO);
        cartBareDTO.setTotal(cart.getTotal());
        cartBareDTO.setTotalTax(cart.getTotal());
        cartBareDTO.setSubtotal(cart.getSubtotal());
        return mapToCartDTO(cart.getCode(), cartBareDTO);
    }

    private static CartDTO mapToCartDTO(String cartCode,
                                        CartBareDTO cartBareDTO) {

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCode(cartCode);
        cartDTO.setCart(cartBareDTO);
        return cartDTO;
    }

    private static List<CartEntry> getCartEntry(List<CartEntryDTO> entriesList,
                                                Cart cart) {

        if (CollectionUtils.isEmpty(entriesList)) {
            return Collections.emptyList();
        }
        return entriesList.stream()
                .filter(Objects::nonNull)
                .map(cartEntryDTO -> mapToCartEntry(cartEntryDTO, cart))
                .collect(Collectors.toList());
    }

    private static List<CartEntryDTO> getCartEntryDTO(List<CartEntry> entriesList) {

        if (CollectionUtils.isEmpty(entriesList)) {
            return Collections.emptyList();
        }
        return entriesList.stream()
                .map(CartEntryMapper::mapToCartEntryDTO)
                .collect(Collectors.toList());
    }

    public static Cart mapToCart(CartBareDTO cartBareDTO) {

        final Optional<Address> shippingAddress = mapToAddress(cartBareDTO.getShippingAddress());
        final Optional<Address> billingAddress = mapToAddress(cartBareDTO.getBillingAddress());
        Cart cart = new Cart();
        final List<CartEntry> cartEntryList = CartMapper.getCartEntry(
                cartBareDTO.getEntriesList(),
                cart
        );
        cart.setCode("Cart-" + UUID.randomUUID().toString());
        cart.setDiscounts(cartBareDTO.getDiscounts());
        cart.setTotal(cartBareDTO.getTotal());
        cart.setEntriesList(cartEntryList);
        cart.setTotalTax(cartBareDTO.getTotal());
        cart.setSubtotal(cartBareDTO.getSubtotal());
        cart.setShippingAddress(shippingAddress.orElse(null));
        cart.setBillingAddress(billingAddress.orElse(null));
        return cart;
    }

}
