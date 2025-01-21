package com.codej.exceptions;

public class DuplicateResourceException  extends RuntimeException {


    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s con %s '%s' ya existe", resourceName, fieldName, fieldValue));
    }
}
