package com.chandranedu.api.cart.mapper;

import com.chandranedu.api.cart.beans.Address;
import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.beans.CartEntry;
import com.chandranedu.api.cart.dto.AddressDTO;
import com.chandranedu.api.cart.dto.CartDTO;
import com.chandranedu.api.cart.dto.CartEntryDTO;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.chandranedu.api.cart.mapper.AddressMapper.mapToAddress;
import static com.chandranedu.api.cart.mapper.AddressMapper.mapToAddressDTO;
import static com.chandranedu.api.cart.mapper.CartEntryMapper.mapToCartEntry;

public class CartMapper {

    private CartMapper() {
        throw new IllegalStateException("CartMapper class");
    }

    public static CartDTO mapToCartDTO(final Cart cart) {

        if (Objects.isNull(cart)) {
            return null;
        }
        final Optional<AddressDTO> billingAddressDTO = mapToAddressDTO(
                cart.getBillingAddress()
        );
        final Optional<AddressDTO> shippingAddressDTO = mapToAddressDTO(
                cart.getShippingAddress()
        );
        final List<CartEntryDTO> entriesListDTO = getCartEntryDTO(
                cart.getEntriesList()
        );

        CartDTO cartDTO = new CartDTO();
        cartDTO.setBillingAddress(billingAddressDTO.orElse(null));
        cartDTO.setShippingAddress(shippingAddressDTO.orElse(null));
        cartDTO.setCode(cart.getCode());
        cartDTO.setDiscounts(cart.getDiscounts());
        cartDTO.setEntriesList(entriesListDTO);
        cartDTO.setTotal(cart.getTotal());
        cartDTO.setTotalTax(cart.getTotal());
        cartDTO.setSubtotal(cart.getSubtotal());
        return cartDTO;
    }

    private static List<CartEntry> getCartEntrys(List<CartEntryDTO> entriesList,
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

    public static Cart mapToCart(CartDTO cartDTO) {

        final Optional<Address> shippingAddress = mapToAddress(cartDTO.getShippingAddress());
        final Optional<Address> billingAddress = mapToAddress(cartDTO.getBillingAddress());
        Cart cart = new Cart();
        final List<CartEntry> cartEntryList = CartMapper.getCartEntrys(
                cartDTO.getEntriesList(),
                cart
        );
        cart.setCode("Cart-" + UUID.randomUUID().toString());
        cart.setDiscounts(cartDTO.getDiscounts());
        cart.setTotal(cartDTO.getTotal());
        cart.setEntriesList(cartEntryList);
        cart.setTotalTax(cartDTO.getTotal());
        cart.setSubtotal(cartDTO.getSubtotal());
        cart.setShippingAddress(shippingAddress.orElse(null));
        cart.setBillingAddress(billingAddress.orElse(null));
        return cart;
    }

}
