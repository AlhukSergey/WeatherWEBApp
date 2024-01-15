package com.alshuk.weather.services.Impl;

import com.alshuk.weather.domain.City;
import com.alshuk.weather.domain.Weather;
import com.alshuk.weather.domain.WeatherParser;
import com.alshuk.weather.domain.WeatherSearchForm;
import com.alshuk.weather.enums.PagesPathEnum;
import com.alshuk.weather.enums.RequestParamEnum;
import com.alshuk.weather.exceptions.BadRequestException;
import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.repositories.CityRepository;
import com.alshuk.weather.repositories.WeatherRepository;
import com.alshuk.weather.services.WeatherService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherParser parser;

    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    @Value("#{${listOfCities}}")
    private List<String> cities;

    @Value("${openWeatherApiKey}")
    private String apiKey;

    public WeatherServiceImpl(WeatherParser parser, WeatherRepository weatherRepository, CityRepository cityRepository) {
        this.parser = parser;
        this.weatherRepository = weatherRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public ModelAndView getLastWeather(City city) throws CityNotFoundException {

        City entity = cityRepository.searchByAnyLanguageName(city.getName());

        if (entity != null) {
            Weather weather = entity.getWeathers().reversed().getFirst();

            ModelMap model = new ModelMap();
            model.addAttribute(RequestParamEnum.CITY_NAME.getValue(), city.getName());
            model.addAttribute(RequestParamEnum.WEATHER.getValue(), weather.getWeather());
            model.addAttribute(RequestParamEnum.ICON.getValue(), weather.getIcon());
            model.addAttribute(RequestParamEnum.TEMPERATURE.getValue(), weather.getTemperature());
            model.addAttribute(RequestParamEnum.CHARACTERISTIC.getValue(), weather.getCharacteristic());
            model.addAttribute(RequestParamEnum.HUMIDITY.getValue(), weather.getHumidity());
            model.addAttribute(RequestParamEnum.PRESSURE.getValue(), weather.getPressure());
            model.addAttribute(RequestParamEnum.VISIBILITY.getValue(), weather.getVisibility());
            model.addAttribute(RequestParamEnum.WIND_SPEED.getValue(), weather.getWindSpeed());

            return new ModelAndView(PagesPathEnum.CURRENT_INFO_PAGE.getPath(), model);
        } else {
            throw new CityNotFoundException("The city not found! Choose another city from the list.");
        }
    }

    @Override
    public ModelAndView getAverageTemperature(WeatherSearchForm form) throws CityNotFoundException {
        City city = cityRepository.searchByAnyLanguageName(form.getName());

        if (city != null) {
            Double averageTemperature = weatherRepository.searchAverageTemp(city.getId(), Timestamp.valueOf(form.getFrom().atStartOfDay()),
                    Timestamp.valueOf(form.getTo().plusDays(1).atStartOfDay()));

            ModelMap model = new ModelMap();
            if (averageTemperature != null) {
                model.addAttribute(RequestParamEnum.CITY_NAME.getValue(), form.getName());
                model.addAttribute(RequestParamEnum.AVERAGE_TEMPERATURE.getValue(), averageTemperature);
            } else {
                model.addAttribute(RequestParamEnum.MESSAGE.getValue(), "There is no data for the selected time period.");
            }
            return new ModelAndView(PagesPathEnum.HISTORY_INFO_PAGE.getPath(), model);
        } else {
            throw new CityNotFoundException("The city not found! Choose another city from the list.");
        }
    }

    @Scheduled(fixedRateString = "${requestPeriod}")
    protected void saveToDB() throws BadRequestException, CityNotFoundException {
        for (String cityName : cities) {
            JsonNode jsonObject = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" +
                    cityName +
                    "&exclude=hourly,daily,minutely,alerts" +
                    "&appid=" +
                    apiKey +
                    "&units=metric");

            if (jsonObject != null) {
                City city = cityRepository.searchByAnyLanguageName(cityName);
                if (city != null) {
                    Weather info = parser.currentWeatherFromJSON(jsonObject, city);

                    weatherRepository.save(info);
                } else {
                    throw new CityNotFoundException("Such city not found in DB! Please, add new city to DB.");
                }
            } else {
                throw new CityNotFoundException("Such city not found!");
            }
        }
    }

    private JsonNode getUrlContent(String s) throws BadRequestException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();

        try {
            URL url = URI.create(s).toURL();
            JsonParser jsonParser = factory.createParser(url);
            return mapper.readTree(jsonParser);
        } catch (IOException e) {
            throw new BadRequestException("Error during getting weather data. The daily request limit has been reached or weather server doesn't work. Try later.");
        }
    }
}
