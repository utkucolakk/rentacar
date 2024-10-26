-- Admin Kullanıcısını Ekleyin
INSERT INTO customer (id, email, first_name, last_name, password, roles, country, city, district, post_code, address_line)
VALUES
(1, 'admin@rentacar.com', 'Admin', 'User', 'admin123', 'ADMIN', 'CountryName', 'CityName', 'DistrictName', '12345', 'Address Line 1');

-- Client Kullanıcısını Ekleyin
INSERT INTO customer (id, email, first_name, last_name, password, roles, country, city, district, post_code, address_line)
VALUES
(2, 'client@rentacar.com', 'Client', 'User', 'client123', 'USER', 'CountryName', 'CityName', 'DistrictName', '54321', 'Address Line 2');


-- BMW Aracını Ekleyin
INSERT INTO cars (id, name, brand_id, color, km, active, available_count, fuel_type, daily_price, image, transmission_type)
VALUES
(1, 'BMW', 1, 'Black', 10000, TRUE, 5, 'GASOLINE', 200, 'C:\\Users\\utii\\Desktop\\bmw4coupe.jpg', 'AUTOMATIC');

-- Audi Aracını Ekleyin
INSERT INTO cars (id, name, brand_id, color, km, active, available_count, fuel_type, daily_price, image, transmission_type)
VALUES
(2, 'Audi', 2, 'Red', 5000, TRUE, 3, 'DIESEL', 250, 'C:\\Users\\utii\\Desktop\\bmw2serisi.jpg', 'MANUAL');



-- Marka Bilgisi
INSERT INTO brands (id, name) VALUES (1, 'BMW');

INSERT INTO brands (id, name) VALUES (2, 'AUDİ');