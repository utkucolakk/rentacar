package com.rentacar.rentacar.service;

import com.rentacar.rentacar.dto.CarRentalRequest;
import com.rentacar.rentacar.dto.RentalCarInfo;
import com.rentacar.rentacar.exception.CarNotFoundException;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.model.CarRental;
import com.rentacar.rentacar.repository.CarRentalRepository;
import com.rentacar.rentacar.repository.CarRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class CarRentalService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarRentalRepository carRentalRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

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
        log.info("Car rental request time {} customer: {}", LocalDateTime.now(), carRentalRequest.getCustomerId());

        // Araç stok kontrolü
        carUnitStockCheck(carRentalRequest.getCarRentalList());

        // Her bir kiralama isteği için işlemleri yapıyoruz
        carRentalRequest.getCarRentalList().forEach(carRentalInfo -> {
            CarRental carRental = new CarRental();

            // Aracı veritabanından al
            Car car = carRepository.getCarById(carRentalInfo.getCarId())
                    .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + carRentalInfo.getCarId()));

            // Kiralama maliyetini gün sayısına göre hesaplama
            LocalDate rentalStart = carRentalInfo.getRentalStartTime();
            LocalDate rentalEnd = carRentalInfo.getRentalEndTime();

            long daysBetween = ChronoUnit.DAYS.between(rentalStart, rentalEnd);

            // Eğer başlangıç ve bitiş tarihi aynıysa, gün sayısını 1 olarak ayarlıyoruz
            if (daysBetween == 0) {
                daysBetween = 1;
            }

            // Kiralama maliyetini hesapla: günlük fiyat * gün sayısı
            Double rentalCost = daysBetween * car.getDailyPrice();
            carRental.setRentalCost(rentalCost);

            // Kullanıcıdan gelen tarih bilgilerini kullanarak başlangıç ve bitiş zamanını ayarla
            carRental.setRentalStartTime(rentalStart.atStartOfDay());  // Kullanıcıdan gelen tarih
            carRental.setRentalEndTime(rentalEnd.atStartOfDay());  // Kullanıcıdan gelen tarih

            // Pickup ve Delivery enum değerlerini ayarla
            carRental.setVehiclePickupPoint(carRentalInfo.getVehiclePickupPoint());
            carRental.setVehicleDeliveryPoint(carRentalInfo.getVehicleDeliveryPoint());

            carRental.setQuantity(carRentalInfo.getQuantity());
            carRental.setCarId(carRentalInfo.getCarId());
            carRental.setCustomerId(carRentalInfo.getCustomerId());


            // Veritabanına kaydet
            carRentalRepository.save(carRental);

            // Aracın stokunu düşür
            car.setAvailableCount(car.getAvailableCount() - carRentalInfo.getQuantity());
            if (car.getAvailableCount() <= 0) {
                car.setActive(false);  // Stok biterse aracı pasif yap
            }
            carRepository.save(car);
        });

        sendMail();
        return true;
    }


    public void sendMail() {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(emailFrom, "Rent a Car ");
            helper.setTo("x");
            helper.setSubject("TEST");
            String content = "<p>Hello,</p><p>This is a test email sent from Spring Boot.</p>";

            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
