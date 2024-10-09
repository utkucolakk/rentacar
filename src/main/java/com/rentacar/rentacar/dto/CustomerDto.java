package com.rentacar.rentacar.dto;

import com.rentacar.rentacar.model.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Address address;
    private String roles;
}