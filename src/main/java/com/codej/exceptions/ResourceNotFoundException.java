package com.codej.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class ResourceNotFoundException extends ResponseStatusException {

    public ResourceNotFoundException(String entityName, UUID id) {
        super(HttpStatus.NOT_FOUND, entityName + " with id " + id + " not found");
    }
}
