package com.rentacar.rentacar.exception;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(String message) {
        super(message);
    }
}
