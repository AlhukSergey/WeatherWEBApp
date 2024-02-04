package com.alshuk.weather.controllers;

import com.alshuk.weather.domain.DTO.CityDTO;
import com.alshuk.weather.domain.DTO.WeatherSearchForm;
import com.alshuk.weather.enums.PagesPathEnum;
import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.exceptions.WeatherExportException;
import com.alshuk.weather.services.WeatherDBService;
import com.alshuk.weather.services.WeatherExportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherDBService weatherDBService;
    private final WeatherExportService exportService;

    @GetMapping
    public ModelAndView openHomePage() {
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath());
    }

    @GetMapping("/international")
    public ModelAndView getInternationalPage() {
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath());
    }

    @PostMapping("/current")
    public ModelAndView getCurrentWeather(@Valid CityDTO city, BindingResult bindingResult, ModelAndView modelAndView) throws CityNotFoundException {
        if (bindingResult.hasErrors()) {
            populateError("name", modelAndView, bindingResult);
            modelAndView.setViewName(PagesPathEnum.HOME_PAGE.getPath());
            return modelAndView;
        }

        return weatherDBService.getLastWeather(city);
    }

    @PostMapping("/period")
    public ModelAndView getPeriodWeather(@Valid WeatherSearchForm form, BindingResult bindingResult, ModelAndView modelAndView) throws CityNotFoundException {
        if (bindingResult.hasErrors()) {
            populateError("name", modelAndView, bindingResult);
            populateError("from", modelAndView, bindingResult);
            populateError("to", modelAndView, bindingResult);
            modelAndView.setViewName(PagesPathEnum.HOME_PAGE.getPath());
            return modelAndView;
        }

        return weatherDBService.getAverageTemperature(form);
    }

    @PostMapping("/export")
    public void exportCityWeather(HttpServletResponse response, @RequestParam("name") String cityName) throws WeatherExportException, CityNotFoundException {
        exportService.exportToFile(response, cityName);
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}
