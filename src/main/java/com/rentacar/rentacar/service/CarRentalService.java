package com.rentacar.rentacar.service;

import com.rentacar.rentacar.dto.CarRentalRequest;
import com.rentacar.rentacar.dto.RentalCarInfo;
import com.rentacar.rentacar.enums.VehicleDeliveryPoint;
import com.rentacar.rentacar.enums.VehiclePickupPoint;
import com.rentacar.rentacar.exception.CarNotFoundException;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.model.CarRental;
import com.rentacar.rentacar.repository.CarRentalRepository;
import com.rentacar.rentacar.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarRentalService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarRentalRepository carRentalRepository;

    private void carUnitStockCheck(List<RentalCarInfo> rentalCarInfoList) {
        rentalCarInfoList.forEach(carInfo -> {
             Car car = carRepository.findById(carInfo.getCarId())
                    .orElseThrow(() -> new CarNotFoundException("car not found id :" + carInfo.getCarId()));

            if (car.getAvailableCount() - carInfo.getQuantity() < 0) {
                log.error("the car stock insufficient : " + carInfo.getCarId());
                throw new RuntimeException("the car stock insufficient carName : " + car.getName());
            }
        });
    }
    public boolean doCarRental(CarRentalRequest carRentalRequest) {
        log.info("CarRental request time {} customer : {}", LocalDateTime.now(), carRentalRequest.getCustomerId());
        carUnitStockCheck(carRentalRequest.getCarRentalList());

        carRentalRequest.getCarRentalList().forEach(carRentalRequestInfo -> {
            CarRental carRental = new CarRental();
            carRental.setCustomerId(carRentalRequest.getCustomerId());
            Car car = carRepository.getActiveCarById(carRentalRequestInfo.getCarId()).orElseThrow(() -> new CarNotFoundException("car not found id :" + carRentalRequestInfo.getCarId()));
            Double rentalCost = carRentalRequestInfo.getQuantity() * car.getDailyPrice();
            carRental.setRentalCost(rentalCost);

            carRental.setQuantity(carRentalRequestInfo.getQuantity());
            carRental.setCarId(carRentalRequestInfo.getCarId());
            carRental.setCustomerId(carRentalRequest.getCustomerId());
            carRental.setRentalStartTime(LocalDateTime.now()); //burayı
            carRental.setRentalEndTime(LocalDateTime.now()); // ve burayı soracağım
            if (car.getAvailableCount() - carRentalRequestInfo.getQuantity() ==0) {
                car.setActive(false);
            }

            carRentalRepository.save(carRental);

            car.setAvailableCount(car.getAvailableCount() - carRentalRequestInfo.getQuantity());
            carRepository.save(car);
        });

        return true;
    }

//---------------------------------------------------------------------------------------------------
@Transactional  // Bu metot bir transaction içinde çalışacak
public boolean processRental(RentalCarInfo rentalCarInfo) {
    Optional<Car> carOptional = carRepository.findById(rentalCarInfo.getCarId());
    if (carOptional.isPresent()) {
        Car car = carOptional.get();

        // Eğer araç mevcutsa, availableCount'u azalt
        if (car.getAvailableCount() > 0) {
            car.setAvailableCount(car.getAvailableCount() - 1);
            carRepository.save(car);

            // Kiralama bilgisini kaydet
            CarRental carRental = new CarRental();
            carRental.setCustomerId(rentalCarInfo.getCarId());
            carRental.setCarId(rentalCarInfo.getCarId());
            carRental.setRentalCost(rentalCarInfo.getQuantity() * car.getDailyPrice());

            // Tarih bilgileri
            carRental.setRentalStartTime(rentalCarInfo.getRentalStartTime().atStartOfDay());
            carRental.setRentalEndTime(rentalCarInfo.getRentalEndTime().atStartOfDay());

            // Enum'ları kaydet
            carRental.setVehiclePickupPoint(VehiclePickupPoint.valueOf(rentalCarInfo.getVehiclePickupPoint()));
            carRental.setVehicleDeliveryPoint(VehicleDeliveryPoint.valueOf(rentalCarInfo.getVehicleDeliveryPoint()));

            carRentalRepository.save(carRental);

            return true;
        }
    }
    return false;
    }

}
