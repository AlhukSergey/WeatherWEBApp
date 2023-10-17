package com.alshuk.weather.exceptions;

public class CityNotFoundException extends Exception{
    public CityNotFoundException(String message) {
        super(message);
    }
}
