package com.rentacar.rentacar.repository;

import com.rentacar.rentacar.model.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.brandId = :brandId AND c.active = true")
    List<Car> findCarListByBrandId(@Param("brandId") Long brandId);

    @Modifying
    @Transactional
    @Query("UPDATE Car c SET c.active = :active WHERE c.id = :id")
    void updateCarActive(@Param("active") Boolean isActive, @Param("id") Long id);

    @Query("SELECT c FROM Car c")
    List<Car> getAllCarList();

    @Query("SELECT c FROM Car c WHERE c.id =:id")
    Optional<Car> getCarById(@Param("id") Long id);


   //@Query("SELECT c FROM Car c WHERE c.active = true AND c.id = id")
    //Optional<Car> getActiveCarById(@Param("id") Long id);



}
