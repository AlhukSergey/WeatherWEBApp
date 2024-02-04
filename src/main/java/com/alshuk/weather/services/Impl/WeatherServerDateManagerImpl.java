package com.alshuk.weather.services.Impl;

import com.alshuk.weather.domain.DTO.WeatherDTO;
import com.alshuk.weather.domain.ExternalWeatherApi;
import com.alshuk.weather.domain.converters.WeatherMapper;
import com.alshuk.weather.domain.entity.City;
import com.alshuk.weather.domain.entity.Weather;
import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.repositories.CityRepository;
import com.alshuk.weather.repositories.WeatherRepository;
import com.alshuk.weather.services.WeatherServerDateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServerDateManagerImpl implements WeatherServerDateManager {
    @Value("#{${listOfCities}}")
    private List<String> cities;
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    private final ExternalWeatherApi externalWeatherApi;
    private final WeatherMapper weatherMapper;

    @Scheduled(fixedRateString = "${requestPeriod}")
    @Override
    public void getServiceWeatherDate() throws CityNotFoundException {
        WeatherDTO weatherDTO;
        City city;
        Weather weather;
        for (String cityName : cities) {
            city = cityRepository.searchByAnyLanguageName(cityName)
                    .orElseThrow(() -> new CityNotFoundException("The city not found in DB. Please add the city with all city names to DB."));
            weatherDTO = externalWeatherApi.getWeather(cityName);

            weather = weatherMapper.toEntity(weatherDTO);
            weather.setCityId(city.getId());
            weatherRepository.save(weather);
        }
    }

    @Override
    public WeatherDTO getServiceWeatherDate(String cityName) {
        return externalWeatherApi.getWeather(cityName);
    }
}
