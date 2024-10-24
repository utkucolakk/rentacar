package com.rentacar.rentacar.model;


import com.rentacar.rentacar.enums.FuelType;
import com.rentacar.rentacar.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "brand_id")
    private Long brandId;

    private String name; // Aracın modeli (örneğin: E300)

    private String color; // Renk bilgisi

    private Double dailyPrice; // Günlük kira ücreti

    private Long availableCount; // Kiralanabilir araç sayısı

    private Long km; // Araç kilometresi

    @Enumerated(EnumType.STRING)
    @Column(name = "transmissionType")
    private TransmissionType transmissionType; // Şanzıman türü (OTOMATİK veya MANUEL)

    @Enumerated(EnumType.STRING)
    @Column(name = "fuelType")
    private FuelType fuelType; // Yakıt türü

    private Boolean active;

    private String image; // Aracın görseli için URL
}
