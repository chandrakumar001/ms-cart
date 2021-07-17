package com.chandranedu.api.cart.service;

import com.chandranedu.api.cart.beans.Cart;
import com.chandranedu.api.cart.beans.CartEntry;
import com.chandranedu.api.cart.dto.CartEntryDTO;
import com.chandranedu.api.cart.repository.CartEntryRepository;
import com.chandranedu.api.exception.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static com.chandranedu.api.cart.controller.Constant.ENTRY_NOT_FOUND;

@Service
public class CartEntryService {

    private final CartEntryRepository cartEntryRepository;

    @Autowired
    public CartEntryService(final CartEntryRepository cartEntryRepository) {

        this.cartEntryRepository = cartEntryRepository;
    }

    public Optional<CartEntry> getCartEntry(String cartEntryCode) {
        return cartEntryRepository.findCartEntryByCode(cartEntryCode);
    }

    public CartEntry addItemInCart(final CartEntryDTO entryDTO,
                                   final Cart cart) {
        CartEntry cartEntry = new CartEntry();
        cartEntry.setCart(cart);
        cartEntry.setCode(entryDTO.getCartEntryCode());
        cartEntry.setQuantity(entryDTO.getQuantity());
        cartEntry.setTotal(getTotal(entryDTO));

        saveCartEntry(cartEntry);
        return cartEntry;
    }

    public Optional<CartEntry> updateItemInCart(CartEntryDTO entryDTO)
            throws EntryNotFoundException {

        final CartEntry entry = cartEntryRepository
                .findCartEntryByCode(entryDTO.getCartEntryCode())
                .orElseThrow(CartEntryService::entryNotFoundException);

        final BigDecimal total = getTotal(entryDTO);
        entry.setQuantity(entryDTO.getQuantity());
        entry.setTotal(total);

        saveCartEntry(entry);
        return Optional.of(entry);
    }

    private BigDecimal getTotal(CartEntryDTO entryDTO) {
        return entryDTO.getPrice().multiply(BigDecimal.valueOf(entryDTO.getQuantity()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public CartEntry saveCartEntry(CartEntry entry) {
        return cartEntryRepository.save(entry);
    }

    private static EntryNotFoundException entryNotFoundException() {
        return new EntryNotFoundException(ENTRY_NOT_FOUND);
    }
}
