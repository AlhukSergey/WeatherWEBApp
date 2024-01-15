package com.alshuk.weather.services;

import com.alshuk.weather.domain.City;
import com.alshuk.weather.domain.WeatherSearchForm;
import com.alshuk.weather.exceptions.CityNotFoundException;
import org.springframework.web.servlet.ModelAndView;

public interface WeatherService {
    ModelAndView getAverageTemperature(WeatherSearchForm form) throws CityNotFoundException;

    ModelAndView getLastWeather(City city) throws CityNotFoundException;
}
