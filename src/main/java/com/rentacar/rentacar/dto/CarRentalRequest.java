package com.rentacar.rentacar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class  CarRentalRequest {

    private Long customerId;

    private List<RentalCarInfo> carRentalList;
}
