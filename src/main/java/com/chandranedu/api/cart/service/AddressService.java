package com.chandranedu.api.cart.service;

import com.chandranedu.api.cart.beans.Address;
import com.chandranedu.api.cart.dto.AddressBareDTO;
import com.chandranedu.api.cart.dto.AddressDTO;
import com.chandranedu.api.cart.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.chandranedu.api.cart.mapper.AddressMapper.mapToAddress;
import static com.chandranedu.api.cart.mapper.AddressMapper.mapToAddressDTO;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    private static RuntimeException addressNotFound() {
        return new RuntimeException("Address not found");
    }

    public List<Address> getAddress() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public AddressDTO saveAddress(AddressBareDTO addressBareDTO) {

        //TODO address validation
        final Address address = mapToAddress(addressBareDTO)
                .orElseThrow(AddressService::addressNotFound);
        final Address saveAddress = addressRepository.save(address);
        return mapToAddressDTO(saveAddress)
                .orElse(null);
    }
}
