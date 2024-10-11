package com.rentacar.rentacar.service;

import com.rentacar.rentacar.exception.CarNotFoundException;
import com.rentacar.rentacar.model.Car;
import com.rentacar.rentacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    private static final String UPLOAD_DIR = "upload";

    public Car createCar(MultipartFile file, Car car) {
        if (Objects.nonNull(file)) {
            String imagePath = saveFile(file, car.getName());
            car.setImage(imagePath);
        }
        return carRepository.save(car);
    }

    public List<Car> getCarListByBrandId(Long brandId) {
        return carRepository.findCarListByBrandId(brandId);
    }

    public Car getCar(Long id) {
        return carRepository.getActiveCarById(id).orElseThrow(() -> new CarNotFoundException("Car Not Found id : " + id));
    }

    private String saveFile(MultipartFile file, String carName) {
        String fileName = carName + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Path.of(UPLOAD_DIR);
        Path filePath;
        try {
            Files.createDirectories(uploadPath);
            filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath.toString();
    }

    public void activeOrDeActiveCar(Long id, boolean isActive) {
        carRepository.updateCarActive(isActive, id);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> getAllCarList() {
        return carRepository.getAllActiveCarList();
    }
}
