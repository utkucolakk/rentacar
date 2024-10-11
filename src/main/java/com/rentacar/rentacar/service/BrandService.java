package com.rentacar.rentacar.service;

import com.rentacar.rentacar.exception.BrandNotFoundException;
import com.rentacar.rentacar.model.Brand;
import com.rentacar.rentacar.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public Brand getBrand(Long id) {
        return brandRepository.findById(id).orElseThrow( () -> new BrandNotFoundException("Brand not found id : " + id));
    }

    public List<Brand> getAllBrandList() {
        return brandRepository.findAll();
    }
}























    /*

        @Autowired
        private brandRepository brandRepository; // Araç sayısı kontrolü için gerekli

        // Yeni araç markası oluşturma
        public Brand createCarBrand(Brand Brand) {
            Optional<Brand> optionalCarBrand = BrandRepository.findBrandByName(Brand.getName());
            if(optionalCarBrand.isPresent()) {
                throw new BrandDuplicateException("Car brand is already defined : " + Brand.getName());
            }
            return carBrandRepository.save(Brand);
        }

        // Araç markasını silme
        public void deleteCarBrand(Long id) {
            Long carCountOfBrand = carRepository.getCarCountOfBrandId(id); // Markaya bağlı araçları kontrol etme
            if(carCountOfBrand > 0) {
                throw new CarBrandDeleteException("You cannot delete this car brand because it has " + carCountOfBrand + " cars associated with it.");
            }
            BrandRepository.deleteById(id);
        }

        // Belirli bir araç markasını görüntüleme
        public Brand getCarBrand(Long id) {
            return BrandRepository.findById(id)
                    .orElseThrow(() -> new BrandNotFoundException("Car brand not found id : " + id));
        }

        // Tüm araç markalarını listeleme
        public List<Brand> getAllCarBrandList() {
            List<Brand> carBrandList = BrandRepository.findAll();
            carBrandList.sort(Comparator.comparingLong(Brand::getId));
            return carBrandList;
        }

        // Araç markasını güncelleme
        public Brand updateCarBrand(Brand carBrand) {
            return BrandRepository.save(carBrand);
        }
    }

}
*/