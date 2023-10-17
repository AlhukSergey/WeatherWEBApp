package com.alshuk.weather.domain;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeatherParser {
    public CurrentWeatherInfo currentWeatherFromJSON(JsonNode jsonObj) {
        return Optional.ofNullable(jsonObj).map(o -> CurrentWeatherInfo.builder()
                        .weather(o.get("weather").elements().next().path("main").asText())
                        .icon("https://openweathermap.org/img/wn/" + o.get("weather").elements().next().path("icon").asText() + "@2x.png")
                        .character(o.get("weather").elements().next().path("description").asText())
                        .temperature(o.path("main").path("temp").asInt())
                        .pressure(o.path("main").path("pressure").asInt())
                        .humidity(o.path("main").path("humidity").asInt())
                        .visibility(o.path("visibility").asInt())
                        .windSpeed(o.path("wind").path("speed").asDouble())
                        .build())
                .orElse(null);
    }

    public PeriodWeatherInfo periodWeatherFromJSON(JsonNode jsonObj) {
        return Optional.ofNullable(jsonObj).map(o -> PeriodWeatherInfo.builder()
                        .temperature((o.get("days")).findValues("temp"))
                        .build())
                .orElse(null);
    }
}
