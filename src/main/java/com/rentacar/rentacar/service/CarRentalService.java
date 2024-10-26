package com.rentacar.rentacar.service;

import com.rentacar.rentacar.dto.CarRentalDto;
import com.rentacar.rentacar.dto.CarRentalRequest;
import com.rentacar.rentacar.dto.RentalCarInfo;
import com.rentacar.rentacar.exception.CarNotFoundException;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.model.CarRental;
import com.rentacar.rentacar.model.Customer;
import com.rentacar.rentacar.repository.CarRentalRepository;
import com.rentacar.rentacar.repository.CarRepository;
import com.rentacar.rentacar.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarRentalService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarRentalRepository carRentalRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomerRepository customerRepository;

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

       // List<Double> orderTotalCostList = new ArrayList<>();

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

           //orderTotalCostList.add(rentalCost);
           //carRental.setRentalCost(rentalCost);

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

       // Customer customer = customerRepository.findById(carRentalRequest.getCustomerId())
       //         .orElseThrow(() ->  new CustomerNotFoundException(carRentalRequest.getCustomerId() + "customer not found!"));

       // Double orderTotalCost = orderTotalCostList.stream().mapToDouble(Double::doubleValue).sum();
       // sendMail(customer.getEmail(), customer.getFirstName(), orderTotalCost );
        return true;
    }

    public List<CarRentalDto> getRentalHistoryByCustomerId(Long customerId) {
        List<CarRental> rentals = carRentalRepository.findByCustomerId(customerId);

        return rentals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CarRentalDto convertToDTO(CarRental carRental) {
        // Car nesnesini al ve null kontrolü yap
        Car car = carRepository.findById(carRental.getCarId()).orElse(null);
        String carName = (car != null) ? car.getName() : "Unknown";

        // Customer nesnesini al ve null kontrolü yap
        Customer customer = customerRepository.findById(carRental.getCustomerId()).orElse(null);
        String customerEmail = (customer != null) ? customer.getEmail() : "Unknown";

        // Null kontrolü yaparak tarihleri al
        LocalDateTime rentalStartTime = Optional.ofNullable(carRental.getRentalStartTime()).orElse(LocalDateTime.now());
        LocalDateTime rentalEndTime = Optional.ofNullable(carRental.getRentalEndTime()).orElse(LocalDateTime.now());

        // CarRentalDto nesnesini dönüştür
        return new CarRentalDto(
                carRental.getId(), // rentalId ekleniyor
                carName,
                customerEmail, // Email, String olmalı
                rentalStartTime,
                rentalEndTime, // Tarih, LocalDateTime olmalı
                carRental.getRentalCost()
        );
    }



   /* public void sendMail(String emailTo, String firstName, double rentalCost ) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(emailFrom, "Rent a Car ");
            helper.setTo(emailTo);
            helper.setSubject("Hello " + firstName + " Your Rent a car" );
            String content = "<p>" + "Hello " + firstName + "</p><p>The rentalCost is" + rentalCost + "</p>";

            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }*/




    public void markAsReturned(Long rentalId) {
        CarRental carRental = carRentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Car Rental not found"));

        Car car = carRepository.findById(carRental.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car not found"));

        // Aracın stokunu artır
        car.setAvailableCount(car.getAvailableCount() + carRental.getQuantity());
        carRepository.save(car);

        // Kiralamanın durumu güncellenebilir (Opsiyonel: Ek bir durum alanı ekleyebilirsiniz)
        carRentalRepository.delete(carRental);  // Teslim alındıysa kaydı silebiliriz veya durumunu güncelleyebiliriz
    }


    public List<CarRentalDto> getOngoingRentals() {
        // Teslim edilmemiş araç kiralamalarını veritabanından al
        List<CarRental> ongoingRentals = carRentalRepository.findByRentalEndTimeAfter(LocalDateTime.now());

        // DTO'ya dönüştür
        return ongoingRentals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    public boolean receiveCar(Long rentalId) {
        // Kiralamayı ID ile bul
        Optional<CarRental> optionalRental = carRentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            CarRental carRental = optionalRental.get();

            // Aracı alınan olarak işaretle ve stok miktarını güncelle
            Car car = carRepository.findById(carRental.getCarId())
                    .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + carRental.getCarId()));

            car.setAvailableCount(car.getAvailableCount() + carRental.getQuantity());
            car.setActive(true); // Stok artarsa aracı tekrar aktif yap
            carRepository.save(car);

            // Kiralamayı veritabanından sil
            carRentalRepository.deleteById(rentalId);

            return true;
        }
        return false;
    }
}
