package com.rentacar.rentacar.controller;

import com.rentacar.rentacar.dto.CarRentalDto;
import com.rentacar.rentacar.dto.CarRentalRequest;
import com.rentacar.rentacar.enums.VehicleDeliveryPoint;
import com.rentacar.rentacar.enums.VehiclePickupPoint;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.model.CarRental;
import com.rentacar.rentacar.repository.CarRentalRepository;
import com.rentacar.rentacar.repository.CarRepository;
import com.rentacar.rentacar.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car-rental")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;

    @Autowired
    private CarRentalRepository carRentalRepository;

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Boolean> doCarRental(@RequestBody CarRentalRequest carRentalRequest) {
        return new ResponseEntity<>(carRentalService.doCarRental(carRentalRequest), HttpStatus.OK);
    }



        @GetMapping("/history/{customerId}")
        public ResponseEntity<List<CarRentalDto>> getRentalHistory(@PathVariable Long customerId) {
            List<CarRentalDto> rentalHistory = carRentalService.getRentalHistoryByCustomerId(customerId);
            return ResponseEntity.ok(rentalHistory);
        }




    @GetMapping("/pickup-points")
    public List<VehiclePickupPoint> getPickupPoints() {
        return Arrays.asList(VehiclePickupPoint.values());
    }

    @GetMapping("/delivery-points")
    public List<VehicleDeliveryPoint> getDeliveryPoints() {
        return Arrays.asList(VehicleDeliveryPoint.values());
    }


   /* @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> test() {
        carRentalService.sendMail("", "", 0d);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/


    @GetMapping("/admin/ongoing-rentals")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<CarRentalDto>> getOngoingRentals() {
        List<CarRentalDto> ongoingRentals = carRentalService.getOngoingRentals();
        return ResponseEntity.ok(ongoingRentals);
    }



    @PutMapping("/admin/receive-car/{rentalId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> receiveCar(@PathVariable Long rentalId) {
        Optional<CarRental> rentalOptional = carRentalRepository.findById(rentalId);

        if (rentalOptional.isPresent()) {
            CarRental ongoingRental = rentalOptional.get();

            // Araç sayısını artır
            Car car = carRepository.findById(ongoingRental.getCarId()).orElse(null);
            if (car != null) {
                car.setAvailableCount(car.getAvailableCount() + 1);
                carRepository.save(car);
            }

            // Kiralama durumu "COMPLETED" olarak güncelle
            ongoingRental.setStatus("COMPLETED");
            carRentalRepository.save(ongoingRental);

            return ResponseEntity.ok("Car received successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found.");
        }
    }


}
