package com.lopez.company.exceptions;

public class EmployeNotFoundException extends Exception {
    public EmployeNotFoundException() {
        super("The employe with the given ID does not exist in the database!!!");
    }
}
