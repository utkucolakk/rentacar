package com.rentacar.rentacar.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalCarInfo {

    private Long carId;
    private int quantity;
    private LocalDate rentalStartTime;
    private LocalDate rentalEndTime;



}
