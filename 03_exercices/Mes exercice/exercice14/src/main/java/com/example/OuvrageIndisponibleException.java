package com.example;

public class OuvrageIndisponibleException extends RuntimeException {
    public OuvrageIndisponibleException(String message) {
        super(message);
    }
}
