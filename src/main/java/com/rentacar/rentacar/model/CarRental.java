package com.rentacar.rentacar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rentacar.rentacar.enums.VehicleDeliveryPoint;
import com.rentacar.rentacar.enums.VehiclePickupPoint;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@Getter
@Setter
public class CarRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "car_id")
    private Long carId;

    private Double rentalCost;

    @Column(name = "rental_start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rentalStartTime;

    @Column(name = "rental_end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rentalEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_pickup_point")
    private VehiclePickupPoint vehiclePickupPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_delivery_point")
    private VehicleDeliveryPoint vehicleDeliveryPoint;

    private int quantity;

}