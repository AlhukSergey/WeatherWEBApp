package com.alshuk.weather.controllers;

import com.alshuk.weather.domain.City;
import com.alshuk.weather.domain.WeatherSearchForm;
import com.alshuk.weather.enums.PagesPathEnum;
import com.alshuk.weather.exceptions.CityNotFoundException;
import com.alshuk.weather.services.WeatherService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@RestController
@RequestMapping("/home")
public class Controller {
    private final WeatherService service;

    public Controller(WeatherService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView openHomePage() {
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath());
    }

    @GetMapping("/international")
    public ModelAndView getInternationalPage() {
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath());
    }

    @PostMapping("/current")
    public ModelAndView getCurrentWeather(@Valid City city, BindingResult bindingResult, ModelAndView modelAndView) throws CityNotFoundException {
        if (bindingResult.hasErrors()) {
            populateError("name", modelAndView, bindingResult);
            modelAndView.setViewName(PagesPathEnum.HOME_PAGE.getPath());
            return modelAndView;
        }

        return service.getLastWeather(city);
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

        return service.getAverageTemperature(form);
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}
