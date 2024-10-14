package com.rentacar.rentacar.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalCarInfo {

    private Long carId;
    private LocalDate rentalStartTime;
    private LocalDate rentalEndTime;
    private String vehiclePickupPoint;
    private String vehicleDeliveryPoint;
    private Double rentalCost;
    private int quantity;
}
