package com.alshuk.weather.exceptions;

import com.alshuk.weather.enums.PagesPathEnum;
import com.alshuk.weather.enums.RequestParamEnum;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundException.class)
    public ModelAndView handlerIncorrectCItyNameException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(RequestParamEnum.ERROR.getValue(), ex.getMessage());
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath(), modelMap);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ModelAndView handlerRequestException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(RequestParamEnum.ERROR.getValue(), ex.getMessage());
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath(), modelMap);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(WeatherExportException.class)
    public ModelAndView handlerExportException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(RequestParamEnum.ERROR.getValue(), ex.getMessage());
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath(), modelMap);
    }
}
