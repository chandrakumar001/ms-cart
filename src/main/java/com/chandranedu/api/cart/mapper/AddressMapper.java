package com.chandranedu.api.cart.mapper;

import com.chandranedu.api.cart.beans.Address;
import com.chandranedu.api.cart.dto.AddressBareDTO;
import com.chandranedu.api.cart.dto.AddressDTO;

import java.util.Objects;
import java.util.Optional;

public class AddressMapper {

    private AddressMapper() {
        throw new IllegalStateException("AddressMapper class");
    }

    public static Optional<Address> mapToAddress(AddressBareDTO addressDTO) {

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

    public static Optional<AddressBareDTO> mapToAddressBareDTO(Address address) {

        if (Objects.isNull(address)) {
            return Optional.empty();
        }
        AddressBareDTO addressDTO = new AddressBareDTO();
        addressDTO.setName(address.getName());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setStreetName(address.getStreetName());
        addressDTO.setStreetNumber(address.getStreetNumber());
        addressDTO.setCountry(address.getCountry());
        return Optional.of(addressDTO);
    }

    public static Optional<AddressDTO> mapToAddressDTO(Address address) {

        if (Objects.isNull(address)) {
            return Optional.empty();
        }
        AddressBareDTO addressBareDTO = new AddressBareDTO();
        addressBareDTO.setName(address.getName());
        addressBareDTO.setPostalCode(address.getPostalCode());
        addressBareDTO.setStreetName(address.getStreetName());
        addressBareDTO.setStreetNumber(address.getStreetNumber());
        addressBareDTO.setCountry(address.getCountry());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddress(addressBareDTO);
        addressDTO.setId(address.getId());
        return Optional.of(addressDTO);
    }

}
