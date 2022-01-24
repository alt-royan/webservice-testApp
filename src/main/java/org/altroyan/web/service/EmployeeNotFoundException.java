package org.altroyan.web.service;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException(String message){
        super(message);
    }
}
