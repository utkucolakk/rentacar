package com.rentacar.rentacar.controller;


import com.rentacar.rentacar.dto.AuthDto;
import com.rentacar.rentacar.dto.CustomerDto;
import com.rentacar.rentacar.dto.LoginDto;
import com.rentacar.rentacar.model.Customer;
import com.rentacar.rentacar.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody AuthDto authDto) {
        return new ResponseEntity<>(customerService.login(authDto), HttpStatus.OK);
    }


}