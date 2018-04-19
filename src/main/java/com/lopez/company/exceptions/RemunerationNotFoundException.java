package com.lopez.company.exceptions;

public class RemunerationNotFoundException extends Exception {
    public RemunerationNotFoundException() {
        super("The remuneration with the given ID does not exist in the database!!!");
    }
}
