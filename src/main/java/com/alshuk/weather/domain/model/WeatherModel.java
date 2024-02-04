package com.alshuk.weather.domain.model;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class WeatherModel {
    private String cityName;
    private Timestamp date;
    private String weather;
    private String icon;
    private String characteristic;
    private int temperature;
    private int humidity;
    private int pressure;
    private double visibility;
    private double windSpeed;
}
