package com.alshuk.weather.services;

import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.exceptions.WeatherExportException;
import jakarta.servlet.http.HttpServletResponse;

public interface WeatherExportService {
    void exportToFile(HttpServletResponse response, String cityName) throws CityNotFoundException, WeatherExportException;
}
