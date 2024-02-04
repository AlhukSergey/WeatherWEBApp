package com.alshuk.weather.services;

import com.alshuk.weather.domain.DTO.WeatherDTO;
import com.alshuk.weather.exceptions.CityNotFoundException;

public interface WeatherServerDateManager {
    void getServiceWeatherDate() throws CityNotFoundException;

    WeatherDTO getServiceWeatherDate(String cityName);
}
