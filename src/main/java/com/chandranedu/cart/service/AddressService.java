package com.chandranedu.cart.service;

import com.chandranedu.cart.beans.Address;
import com.chandranedu.cart.dto.AddressDTO;
import com.chandranedu.cart.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.chandranedu.cart.mapper.AddressMapper.mapToAddress;
import static com.chandranedu.cart.mapper.AddressMapper.mapToAddressDTO;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
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

    public AddressDTO saveAddress(AddressDTO addressDTO) {

        //TODO address validation
        final Optional<Address> address = mapToAddress(addressDTO);
        if (address.isEmpty()) {
            throw new RuntimeException("Heelo");
        }
        final Address saveAddress = addressRepository.save(address.get());
        final Optional<AddressDTO> addressDTO1 = mapToAddressDTO(saveAddress);
        return addressDTO1.get();
    }
}
