package com.rentacar.rentacar.controller;



import com.rentacar.rentacar.model.Brand;
import com.rentacar.rentacar.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.createBrand(brand), HttpStatus.CREATED);
    }
}























    /*
    @Autowired
    private BrandService BrandService;

    // Yeni araç markası oluşturma - Yalnızca ADMIN yetkisine sahip
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CarBrand> createCarBrand(@RequestBody CarBrand carBrand) {
        return new ResponseEntity<>(carBrandService.createCarBrand(carBrand), HttpStatus.CREATED);
    }

    // Araç markasını silme - Yalnızca ADMIN yetkisine sahip
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCarBrand(@PathVariable("id") Long id) {
        carBrandService.deleteCarBrand(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Belirli bir araç markasını görüntüleme - Hem USER hem ADMIN erişebilir
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CarBrand> getCarBrand(@PathVariable("id") Long id) {
        return new ResponseEntity<>(carBrandService.getCarBrand(id), HttpStatus.OK);
    }

    // Tüm araç markalarını listeleme - Hem USER hem ADMIN erişebilir
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<CarBrand>> getAllCarBrandList() {
        return new ResponseEntity<>(carBrandService.getAllCarBrandList(), HttpStatus.OK);
    }

    // Araç markasını güncelleme - Yalnızca ADMIN yetkisine sahip
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CarBrand> updateCarBrand(@RequestBody CarBrand carBrand) {
        return new ResponseEntity<>(carBrandService.updateCarBrand(carBrand), HttpStatus.OK);
    }
}
*/