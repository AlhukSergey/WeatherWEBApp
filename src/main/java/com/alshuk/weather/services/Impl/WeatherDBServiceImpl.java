package com.alshuk.weather.services.Impl;

import com.alshuk.weather.domain.DTO.CityDTO;
import com.alshuk.weather.domain.DTO.WeatherDTO;
import com.alshuk.weather.domain.DTO.WeatherSearchForm;
import com.alshuk.weather.domain.converters.WeatherMapper;
import com.alshuk.weather.domain.entity.City;
import com.alshuk.weather.domain.entity.Weather;
import com.alshuk.weather.enums.PagesPathEnum;
import com.alshuk.weather.enums.RequestParamEnum;
import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.repositories.CityRepository;
import com.alshuk.weather.repositories.WeatherRepository;
import com.alshuk.weather.services.WeatherDBService;
import com.alshuk.weather.services.WeatherServerDateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class WeatherDBServiceImpl implements WeatherDBService {
    private final WeatherServerDateManager weatherServerDateManager;
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    private final WeatherMapper weatherMapper;


    @Override
    public ModelAndView getLastWeather(CityDTO city) throws CityNotFoundException {

        City cityEntity = cityRepository.searchByAnyLanguageName(city.getName()).orElseThrow(() -> new CityNotFoundException("The city not found! Choose another city from the list."));
        Weather weatherEntity = weatherRepository.findLastCityWeather(cityEntity.getId()).orElse(null);
        WeatherDTO weatherDTO;
        if (weatherEntity != null) {
            weatherDTO = weatherMapper.toDTO(weatherEntity);
            weatherDTO.setCityName(city.getName());
        } else {
            weatherDTO = weatherServerDateManager.getServiceWeatherDate(city.getName());
            Weather createdWeatherEntity = weatherMapper.toEntity(weatherDTO);
            createdWeatherEntity.setCityId(cityEntity.getId());
            weatherRepository.save(createdWeatherEntity);
        }
        return setOnPage(weatherDTO);
    }

    @Override
    public ModelAndView getAverageTemperature(WeatherSearchForm form) throws CityNotFoundException {
        City city = cityRepository.searchByAnyLanguageName(form.getName()).orElseThrow(() -> new CityNotFoundException("The city not found! Choose another city from the list."));

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
    }

    private ModelAndView setOnPage(WeatherDTO weatherDTO) {
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamEnum.CITY_NAME.getValue(), weatherDTO.getCityName());
        model.addAttribute(RequestParamEnum.WEATHER.getValue(), weatherDTO.getWeather());
        model.addAttribute(RequestParamEnum.ICON.getValue(), weatherDTO.getIcon());
        model.addAttribute(RequestParamEnum.TEMPERATURE.getValue(), weatherDTO.getTemperature());
        model.addAttribute(RequestParamEnum.CHARACTERISTIC.getValue(), weatherDTO.getCharacteristic());
        model.addAttribute(RequestParamEnum.HUMIDITY.getValue(), weatherDTO.getHumidity());
        model.addAttribute(RequestParamEnum.PRESSURE.getValue(), weatherDTO.getPressure());
        model.addAttribute(RequestParamEnum.VISIBILITY.getValue(), weatherDTO.getVisibility());
        model.addAttribute(RequestParamEnum.WIND_SPEED.getValue(), weatherDTO.getWindSpeed());

        return new ModelAndView(PagesPathEnum.CURRENT_INFO_PAGE.getPath(), model);
    }
}
