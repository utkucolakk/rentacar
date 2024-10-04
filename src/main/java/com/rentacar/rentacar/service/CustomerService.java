package com.rentacar.rentacar.service;

import com.rentacar.rentacar.enums.RoleEnum;
import com.rentacar.rentacar.model.Customer;
import com.rentacar.rentacar.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public Customer createCustomer(Customer customer) {
        if(Objects.isNull(customer.getRoles())) {
            customer.setRoles(RoleEnum.ROLE_USER.toString());
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

}