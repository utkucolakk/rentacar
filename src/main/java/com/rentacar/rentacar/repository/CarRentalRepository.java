package com.rentacar.rentacar.repository;

import com.rentacar.rentacar.model.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalRepository extends JpaRepository<CarRental, Long> {
}
