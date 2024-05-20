package com.wsd.commerce.model.exceptions;

public class ResourceNotFoundException extends RuntimeException {

   public ResourceNotFoundException(String message) {
        super(message);
    }
}
