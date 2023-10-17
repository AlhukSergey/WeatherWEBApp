package com.alshuk.weather.services;

import com.alshuk.weather.domain.City;
import com.alshuk.weather.domain.PeriodWeatherSearchForm;
import com.alshuk.weather.exceptions.BadRequestException;
import com.alshuk.weather.exceptions.CityNotFoundException;
import org.springframework.web.servlet.ModelAndView;

public interface WeatherService {
    ModelAndView getInfo(City city) throws CityNotFoundException, BadRequestException;

    ModelAndView getInfo(PeriodWeatherSearchForm form) throws CityNotFoundException, BadRequestException;
}
