package com.lopez.company.exceptions;

public class PositionNotFoundException extends Exception {
    public PositionNotFoundException() {
        super("The position with the given ID does not exist in the database!!!");
    }
}
