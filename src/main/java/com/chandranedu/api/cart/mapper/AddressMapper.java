package com.chandranedu.api.cart.mapper;

import com.chandranedu.api.cart.beans.Address;
import com.chandranedu.api.cart.dto.AddressDTO;

import java.util.Objects;
import java.util.Optional;

public class AddressMapper {

    public static Optional<Address> mapToAddress(AddressDTO addressDTO) {

        if (Objects.isNull(addressDTO)) {
            return Optional.empty();
        }
        Address address = new Address();
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setStreetName(addressDTO.getStreetName());
        address.setName(addressDTO.getName());
        address.setCountry(addressDTO.getCountry());
        address.setPostalCode(addressDTO.getPostalCode());
        return Optional.of(address);
    }

    public static Optional<AddressDTO> mapToAddressDTO(Address address) {

        if (Objects.isNull(address)) {
            return Optional.empty();
        }
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setName(address.getName());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setStreetName(address.getStreetName());
        addressDTO.setStreetNumber(address.getStreetNumber());
        addressDTO.setCountry(address.getCountry());
        return Optional.of(addressDTO);
    }
}
