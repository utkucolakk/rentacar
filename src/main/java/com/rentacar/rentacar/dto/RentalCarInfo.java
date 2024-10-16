package com.rentacar.rentacar.dto;

import com.rentacar.rentacar.enums.VehicleDeliveryPoint;
import com.rentacar.rentacar.enums.VehiclePickupPoint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalCarInfo {

    private Long customerId;
    private Long carId;
    private LocalDate rentalStartTime;
    private LocalDate rentalEndTime;
    private VehiclePickupPoint vehiclePickupPoint;
    private VehicleDeliveryPoint vehicleDeliveryPoint;
    private Double rentalCost;
    private int quantity;
}
