package com.alshuk.weather.services.Impl;

import com.alshuk.weather.domain.City;
import com.alshuk.weather.domain.CurrentWeatherInfo;
import com.alshuk.weather.domain.PeriodWeatherInfo;
import com.alshuk.weather.domain.PeriodWeatherSearchForm;
import com.alshuk.weather.domain.WeatherParser;
import com.alshuk.weather.enums.AppEnum;
import com.alshuk.weather.enums.PagesPathEnum;
import com.alshuk.weather.enums.RequestParamEnum;
import com.alshuk.weather.exceptions.BadRequestException;
import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.services.WeatherService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherParser parser;

    public WeatherServiceImpl(WeatherParser parser) {
        this.parser = parser;
    }

    @Override
    public ModelAndView getInfo(City city) throws CityNotFoundException, BadRequestException {
        JsonNode jsonObject = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" +
                city.getName() +
                "&appid=" +
                AppEnum.OPEN_WEATHER_API_KEY.getKey() +
                "&units=metric");


        if (jsonObject != null) {
            CurrentWeatherInfo info = parser.currentWeatherFromJSON(jsonObject);

            ModelMap model = new ModelMap();
            model.addAttribute(RequestParamEnum.CITY_NAME.getValue(), city.getName());
            model.addAttribute(RequestParamEnum.WEATHER.getValue(), info.getWeather());
            model.addAttribute(RequestParamEnum.ICON.getValue(), info.getIcon());
            model.addAttribute(RequestParamEnum.TEMPERATURE.getValue(), info.getTemperature());
            model.addAttribute(RequestParamEnum.CHARACTERISTIC.getValue(), info.getCharacter());
            model.addAttribute(RequestParamEnum.HUMIDITY.getValue(), info.getHumidity());
            model.addAttribute(RequestParamEnum.PRESSURE.getValue(), info.getPressure());
            model.addAttribute(RequestParamEnum.VISIBILITY.getValue(), info.getVisibility());
            model.addAttribute(RequestParamEnum.WIND_SPEED.getValue(), info.getWindSpeed());

            return new ModelAndView(PagesPathEnum.CURRENT_INFO_PAGE.getPath(), model);
        } else {
            throw new CityNotFoundException("Such city not found!");
        }
    }

    @Override
    public ModelAndView getInfo(PeriodWeatherSearchForm form) throws CityNotFoundException, BadRequestException {
        JsonNode jsonObject = getUrlContent(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" +
                        form.getName() +
                        "/" +
                        form.getFrom() +
                        "/" +
                        form.getTo() +
                        "?unitGroup=metric&include=days&key=" +
                        AppEnum.VISUAL_CROSSING_API_KEY.getKey() +
                        "&contentType=json"
        );

        if (jsonObject != null) {
            PeriodWeatherInfo info = parser.periodWeatherFromJSON(jsonObject);
            double averageTemperature = info.getTemperature().stream().mapToDouble(JsonNode::doubleValue).average().orElse(0);

            ModelMap model = new ModelMap();
            model.addAttribute(RequestParamEnum.CITY_NAME.getValue(), form.getName());
            model.addAttribute(RequestParamEnum.AVERAGE_TEMPERATURE.getValue(), averageTemperature);

            return new ModelAndView(PagesPathEnum.HISTORY_INFO_PAGE.getPath(), model);
        }
        throw new CityNotFoundException("Such city not found!");
    }

    private JsonNode getUrlContent(String s) throws BadRequestException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();

        try {
            URL url = URI.create(s).toURL();
            JsonParser jsonParser = factory.createParser(url);
            return mapper.readTree(jsonParser);
        } catch (IOException e) {
            throw new BadRequestException("Unexpected error during getting weather data. Try later.");
        }
    }
}
