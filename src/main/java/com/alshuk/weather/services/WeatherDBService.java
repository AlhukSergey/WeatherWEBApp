package com.alshuk.weather.services;

import com.alshuk.weather.domain.DTO.CityDTO;
import com.alshuk.weather.domain.DTO.WeatherSearchForm;
import com.alshuk.weather.exceptions.CityNotFoundException;
import org.springframework.web.servlet.ModelAndView;

public interface WeatherDBService {
    ModelAndView getAverageTemperature(WeatherSearchForm form) throws CityNotFoundException;

    ModelAndView getLastWeather(CityDTO city) throws CityNotFoundException;
}
