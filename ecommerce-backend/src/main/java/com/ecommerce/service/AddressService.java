package com.ecommerce.service;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Get logged-in user
    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // Get all addresses
    public List<Address> getAllAddresses(String email) {

        User user = getUser(email);

        return addressRepository.findByUser(user);
    }

    // Get address by ID
    public Address getAddressById(
            String email,
            Long id) {

        User user = getUser(email);

        return addressRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Address not found"));
    }

    // Add address
    public Address addAddress(
            String email,
            Address address) {

        User user = getUser(email);

        address.setId(null);
        address.setUser(user);

        if (address.getCountry() == null ||
                address.getCountry().isBlank()) {

            address.setCountry("India");
        }

        return addressRepository.save(address);
    }

    // Update address
    public Address updateAddress(
            String email,
            Long id,
            Address updatedAddress) {

        Address existingAddress =
                getAddressById(email, id);

        existingAddress.setFullName(
                updatedAddress.getFullName());

        existingAddress.setPhone(
                updatedAddress.getPhone());

        existingAddress.setAddressLine(
                updatedAddress.getAddressLine());

        existingAddress.setCity(
                updatedAddress.getCity());

        existingAddress.setState(
                updatedAddress.getState());

        existingAddress.setPostalCode(
                updatedAddress.getPostalCode());

        existingAddress.setCountry(
                updatedAddress.getCountry());

        existingAddress.setAddressType(
                updatedAddress.getAddressType());

        return addressRepository.save(existingAddress);
    }

    // Delete address
    public void deleteAddress(
            String email,
            Long id) {

        Address address =
                getAddressById(email, id);

        addressRepository.delete(address);
    }
}