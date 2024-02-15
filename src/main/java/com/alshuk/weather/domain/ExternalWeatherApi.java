package com.alshuk.weather.domain;

import com.alshuk.weather.domain.DTO.WeatherDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather")
@CircuitBreaker(name = "weather-breaker")
@Retry(name = "weather-retry")
public interface ExternalWeatherApi {
    @GetMapping
    WeatherDTO getWeather(@RequestParam(name = "q") String cityName);
}
