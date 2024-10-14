package com.rentacar.rentacar.repository;

import com.rentacar.rentacar.model.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRentalRepository extends JpaRepository<CarRental, Long> {


        // Eğer car_id'ye göre sorgulama yapmak isterseniz
        List<CarRental> findByCarId(Long carId);

        // Eğer customer_id'ye göre sorgulama yapmak isterseniz
        List<CarRental> findByCustomerId(Long customerId);



}
