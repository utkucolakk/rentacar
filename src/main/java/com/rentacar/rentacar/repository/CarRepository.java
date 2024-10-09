package com.rentacar.rentacar.repository;

import com.rentacar.rentacar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.brandId = :brandId")
    List<Car> findCarListByBrandId(@Param("brandId") Long brandId);

    @Query("UPDATE FROM Car c SET c.active = :active WHERE c.id = :id")
    Boolean updateCarActive(@Param("active") Boolean isActive, @Param("id") Long id);



}
