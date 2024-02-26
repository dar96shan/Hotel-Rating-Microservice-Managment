package com.micro.HotelService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource not found on server...!!!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
