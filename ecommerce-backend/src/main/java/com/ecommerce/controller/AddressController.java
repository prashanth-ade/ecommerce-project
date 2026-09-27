package com.ecommerce.controller;

import com.ecommerce.entity.Address;
import com.ecommerce.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Get all addresses
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                addressService.getAllAddresses(email)
        );
    }

    // Get address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                addressService.getAddressById(email, id)
        );
    }

    // Add address
    @PostMapping
    public ResponseEntity<Address> addAddress(
            @RequestBody Address address,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                addressService.addAddress(email, address)
        );
    }

    // Update address
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable Long id,
            @RequestBody Address address,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                addressService.updateAddress(
                        email,
                        id,
                        address
                )
        );
    }

    // Delete address
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        addressService.deleteAddress(email, id);

        return ResponseEntity.ok(
                "Address deleted successfully"
        );
    }
}