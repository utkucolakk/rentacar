package com.rentacar.rentacar.repository;

import com.rentacar.rentacar.model.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CarRentalRepository extends JpaRepository<CarRental, Long> {

        // Mevcut kiralamaları bulmak için müşteri ID'ye göre sorgu
        List<CarRental> findByCustomerId(Long customerId);

        // Car ID'ye göre kiralamaları bulmak için sorgu
        List<CarRental> findByCarId(Long carId);

        // Teslim alınmamış (hala devam eden) kiralamaları bulmak için
        @Query("SELECT cr FROM CarRental cr WHERE cr.rentalEndTime > :now")
        List<CarRental> findOngoingRentals(@Param("now") LocalDateTime now);

        // Teslim edilmemiş kiralamaları getiren bir metot
        List<CarRental> findByRentalEndTimeAfter(LocalDateTime now);








}
