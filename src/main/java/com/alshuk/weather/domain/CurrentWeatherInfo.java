package com.alshuk.weather.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentWeatherInfo {
    @NotNull
    private String weather;
    @NotNull
    private String icon;
    @NotNull
    private String character;
    @NotNull
    private int temperature;
    @NotNull
    private int humidity;
    @NotNull
    private int pressure;
    @NotNull
    private double visibility;
    @NotNull
    private double windSpeed;
}
