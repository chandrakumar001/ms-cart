package com.chandranedu.api.cart.service;

import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.beans.CartEntry;
import com.chandranedu.api.cart.dto.AddressDTO;
import com.chandranedu.api.cart.dto.CartDTO;
import com.chandranedu.api.cart.dto.CartEntryDTO;
import com.chandranedu.api.cart.mapper.CartEntryMapper;
import com.chandranedu.api.cart.repository.CartEntryRepository;
import com.chandranedu.api.exception.CartEntryException;
import com.chandranedu.api.exception.CartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.chandranedu.api.cart.mapper.AddressMapper.mapToAddressDTO;
import static com.chandranedu.api.cart.mapper.CartEntryMapper.mapToCartEntry;
import static java.util.stream.Collectors.toList;

@Service
public class CartEntryService {

    private final CartService cartService;
    private final CartEntryRepository cartEntryRepository;

    @Autowired
    public CartEntryService(final CartService cartService,
                            final CartEntryRepository cartEntryRepository) {

        this.cartEntryRepository = cartEntryRepository;
        this.cartService = cartService;
    }

    public CartDTO addItemToCart(final String code,
                                 final CartEntryDTO cartEntryDTO)
            throws CartEntryException, CartNotFoundException {

        final Cart cart = cartService.getCartByCode(code)
                .orElseThrow(this::cartNotFoundException);

        itemAlreadyInCart(cartEntryDTO.getCartEntryCode());

        final CartEntry cartEntry = mapToCartEntry(cartEntryDTO, cart);
        final CartEntry saveCartEntry = cartEntryRepository.save(cartEntry);
        return mapToCartDTO(saveCartEntry.getCart());
    }

    private void itemAlreadyInCart(String cartEntryCode) throws CartEntryException {

        final Optional<CartEntry> cartEntryOptional = getCartEntry(
                cartEntryCode
        );
        if (cartEntryOptional.isPresent()) {
            throw new CartEntryException("Entry Already Present");
        }
    }

    public CartDTO getCartsByCartCode(String code) throws CartNotFoundException {

        final Cart cart = cartService.getCartByCode(code)
                .orElseThrow(this::cartNotFoundException);
        return mapToCartDTO(cart);
    }

    public CartDTO getCartsByCartCodeAndEntries(String code)
            throws CartNotFoundException {

        final Cart cart = cartService.getCartByCode(code)
                .orElseThrow(this::cartNotFoundException);
        return mapToCartDTO(cart);
    }


    private CartNotFoundException cartNotFoundException() {
        return new CartNotFoundException("Cart Not Found");
    }


    private static CartEntry getCartEntry(CartEntryDTO entryDTO,
                                          Cart cart) {

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCart(cart);
        cartEntry.setCode(entryDTO.getCartEntryCode());
        cartEntry.setQuantity(entryDTO.getQuantity());
        cartEntry.setTotal(entryDTO.getTotal().multiply(BigDecimal.valueOf(entryDTO.getQuantity()))
                .setScale(2, RoundingMode.HALF_UP));
        return cartEntry;
    }

    private CartDTO mapToCartDTO(final Cart cart) {

        final Optional<AddressDTO> billingAddressDTO = mapToAddressDTO(
                cart.getBillingAddress()
        );
        final Optional<AddressDTO> shippingAddressDTO = mapToAddressDTO(
                cart.getShippingAddress()
        );
        final List<CartEntryDTO> entriesListDTO = getCartEntryDTOS(
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

    private List<CartEntryDTO> getCartEntryDTOS(List<CartEntry> cartEntryList) {

        if (CollectionUtils.isEmpty(cartEntryList)) {
            return Collections.emptyList();
        }
        return cartEntryList.
                stream()
                .map(CartEntryMapper::mapToCartEntryDTO)
                .collect(toList());
    }

    public Optional<CartEntry> getCartEntry(String cartEntryCode) {
        return cartEntryRepository.findCartEntryByCode(cartEntryCode);
    }
}
