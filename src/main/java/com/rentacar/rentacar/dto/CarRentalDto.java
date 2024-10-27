package com.rentacar.rentacar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CarRentalDto {

    private Long rentalId;  // Yeni rentalId alanı

    private String carName; // Kiralanan araç ismi

    private String customerEmail; // Müşteri adı yerine e-posta

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime rentalStartTime; // Kiralama başlangıç zamanı

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime rentalEndTime; // Kiralama bitiş zamanı
    
    private Double rentalCost; // Kiralama maliyeti

    private String status;

    // Constructor
    public CarRentalDto(Long rentalId, String carName, String customerEmail, LocalDateTime rentalStartTime, LocalDateTime rentalEndTime, Double rentalCost, String status) {
        this.rentalId = rentalId;
        this.carName = carName;
        this.customerEmail = customerEmail; // Bu satırı ekleyin
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
        this.rentalCost = rentalCost;
        this.status = status;

    }

    // Getter ve Setter'lar

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public LocalDateTime getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(LocalDateTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public LocalDateTime getRentalEndTime() {
        return rentalEndTime;
    }

    public void setRentalEndTime(LocalDateTime rentalEndTime) {
        this.rentalEndTime = rentalEndTime;
    }

    public Double getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(Double rentalCost) {
        this.rentalCost = rentalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}




