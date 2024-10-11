package com.rentacar.rentacar.controller;

import com.rentacar.rentacar.dto.CarRentalRequest;
import com.rentacar.rentacar.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car rental")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Boolean> doCarRental(@RequestBody CarRentalRequest carRentalRequest) {
        return new ResponseEntity<>(carRentalService.doCarRental(carRentalRequest), HttpStatus.OK);
    }
}
