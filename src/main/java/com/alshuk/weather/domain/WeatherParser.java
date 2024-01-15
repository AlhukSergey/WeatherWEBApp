package com.alshuk.weather.domain;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class WeatherParser {
    public Weather currentWeatherFromJSON(JsonNode jsonObj, City city) {
        return Optional.ofNullable(jsonObj).map(o -> Weather.builder()
                        .date(Timestamp.valueOf(convertToLocalDateTime(o.get("dt").asText())))
                        .weather(o.get("weather").elements().next().path("main").asText())
                        .icon("https://openweathermap.org/img/wn/" + o.get("weather").elements().next().path("icon").asText() + "@2x.png")
                        .characteristic(o.get("weather").elements().next().path("description").asText())
                        .temperature(o.path("main").path("temp").asInt())
                        .pressure(o.path("main").path("pressure").asInt())
                        .humidity(o.path("main").path("humidity").asInt())
                        .visibility(o.path("visibility").asInt())
                        .windSpeed(o.path("wind").path("speed").asDouble())
                        .cityId(city.getId())
                        .build())
                .orElse(null);
    }

    private LocalDateTime convertToLocalDateTime(String unixTimeString) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(unixTimeString)),
                ZoneId.systemDefault());
    }
}
