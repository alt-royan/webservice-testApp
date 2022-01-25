package org.altroyan.web.service.web.controller;

import org.altroyan.web.service.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class EmployeeControllerAdvice {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> notFoundHandler(EmployeeNotFoundException exception){
        Map<String, String> error =new HashMap<>();
        error.put("timestamp", new Timestamp(System.currentTimeMillis()).toString());
        error.put("status", HttpStatus.NOT_FOUND.toString());
        error.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
