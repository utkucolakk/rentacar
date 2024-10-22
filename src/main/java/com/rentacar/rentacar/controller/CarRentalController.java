package com.rentacar.rentacar.controller;

import com.rentacar.rentacar.dto.CarRentalRequest;
import com.rentacar.rentacar.dto.RentalCarInfo;
import com.rentacar.rentacar.enums.VehicleDeliveryPoint;
import com.rentacar.rentacar.enums.VehiclePickupPoint;
import com.rentacar.rentacar.model.Brand;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/car-rental")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Boolean> doCarRental(@RequestBody CarRentalRequest carRentalRequest) {
        return new ResponseEntity<>(carRentalService.doCarRental(carRentalRequest), HttpStatus.OK);
    }



    @GetMapping("/pickup-points")
    public List<VehiclePickupPoint> getPickupPoints() {
        return Arrays.asList(VehiclePickupPoint.values());
    }

    @GetMapping("/delivery-points")
    public List<VehicleDeliveryPoint> getDeliveryPoints() {
        return Arrays.asList(VehicleDeliveryPoint.values());
    }


    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> test() {
        carRentalService.sendMail();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
