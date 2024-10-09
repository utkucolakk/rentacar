package com.rentacar.rentacar.controller;

import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping(name = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Car> createCar(@RequestPart("file")MultipartFile file,
                                         @ModelAttribute Car car) {
        return new ResponseEntity<>(carService.createCar(file, car), HttpStatus.CREATED);
    }

    @GetMapping("/brand/{brandId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Car>> getCarList(@PathVariable(value = "brandId") Long brandId) {
        return new ResponseEntity<>(carService.getCarListByBrandId(brandId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Car> getCar(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(carService.getCar(id), HttpStatus.OK);
    }

    @PutMapping(name = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Car> updateCar(@RequestPart(value = "file", required = false)MultipartFile file,
                                         @ModelAttribute Car car) {
        return new ResponseEntity<>(carService.createCar(file, car), HttpStatus.OK);
    }

    @PutMapping("active/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> activeCar(@PathVariable("id") Long id) {
        return new ResponseEntity<>(carService.activeOrDeActiveCar(id, true), HttpStatus.OK);

    }

    @PutMapping("deActive/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean > deActiveCar(@PathVariable("id") Long id) {
        return new ResponseEntity<>(carService.activeOrDeActiveCar(id, false), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Car>> getAllCarList() {
        return new ResponseEntity<>(carService.getAllCarList(), HttpStatus.OK);
    }

}
