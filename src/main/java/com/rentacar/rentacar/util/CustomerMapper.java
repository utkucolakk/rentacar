package com.rentacar.rentacar.util;

import com.rentacar.rentacar.dto.CustomerDto;
import com.rentacar.rentacar.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDto customerToCustomerDto(Customer customer);
}
