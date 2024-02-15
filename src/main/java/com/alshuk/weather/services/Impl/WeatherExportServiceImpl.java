package com.alshuk.weather.services.Impl;

import com.alshuk.weather.domain.DTO.WeatherDTO;
import com.alshuk.weather.domain.converters.WeatherMapper;
import com.alshuk.weather.domain.entity.City;
import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.exceptions.WeatherExportException;
import com.alshuk.weather.repositories.CityRepository;
import com.alshuk.weather.services.WeatherExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherExportServiceImpl implements WeatherExportService {
    private final CityRepository cityRepository;
    private final WeatherMapper weatherMapper;

    @Override
    public void exportToFile(HttpServletResponse response, String cityName) throws CityNotFoundException, WeatherExportException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=weatherOfCityWithCityName_" + cityName + ".csv";
        response.setHeader(headerKey, headerValue);

        City city = cityRepository.searchByAnyLanguageName(cityName).orElseThrow(() -> new CityNotFoundException("The city not found. Please check city name and try again."));

        List<WeatherDTO> weatherDTOList = city.getWeathers().stream().map(weatherMapper::toDTO).toList();

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"Date", "Weather", "Characteristic", "Temperature", "Humidity", "Pressure", "Visibility", "Wind speed", "Icon"};
            String[] nameMapping = {"date", "weather", "characteristic", "temperature", "humidity", "pressure", "visibility", "windSpeed", "icon"};

            csvWriter.writeHeader(csvHeader);

            for (WeatherDTO weatherDTO : weatherDTOList) {
                csvWriter.write(weatherDTO, nameMapping);
            }
        } catch (IOException e) {
            throw new WeatherExportException("An error occurred while writing the file. Try again.");
        }
    }
}
