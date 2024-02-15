package com.alshuk.weather.domain.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class WeatherDTO {
    @NotNull
    @CsvBindByName
    private String cityName;
    @NotNull
    @CsvBindByName
    private Timestamp date;
    @NotNull
    @CsvBindByName
    private String weather;
    @NotNull
    @CsvBindByName
    private String icon;
    @NotNull
    @CsvBindByName
    private String characteristic;
    @NotNull
    @CsvBindByName
    private int temperature;
    @NotNull
    @CsvBindByName
    private int humidity;
    @NotNull
    @CsvBindByName
    private int pressure;
    @NotNull
    @CsvBindByName
    private double visibility;
    @NotNull
    @CsvBindByName
    private double windSpeed;

    private WeatherDTO(@JsonProperty("name") String cityName,
                       @JsonProperty("dt") String dateTime,
                       @JsonProperty("weather") OpenWeatherApiWeatherDesc[] list,
                       @JsonProperty("main") OpenWeatherApiMainDesc openWeatherApiMainDesc,
                       @JsonProperty("wind") OpenWeatherApiWindDesc openWeatherApiWindDesc,
                       @JsonProperty("visibility") double visibility) {
        this.cityName = cityName;
        this.date = Timestamp.valueOf(convertToLocalDateTime(dateTime));
        this.weather = list[0].getMain();
        this.icon = list[0].getIcon();
        this.characteristic = list[0].getDescription();
        this.temperature = openWeatherApiMainDesc.getTemperature();
        this.humidity = openWeatherApiMainDesc.getHumidity();
        this.pressure = openWeatherApiMainDesc.getPressure();
        this.visibility = visibility;
        this.windSpeed = openWeatherApiWindDesc.getSpeed();
    }

    private LocalDateTime convertToLocalDateTime(String unixTimeString) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(unixTimeString)),
                ZoneId.systemDefault());
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpenWeatherApiMainDesc {
        private int temperature;
        private int pressure;
        private int humidity;

        public OpenWeatherApiMainDesc(@JsonProperty("temp") int temperature,
                                      @JsonProperty("pressure") int pressure,
                                      @JsonProperty("humidity") int humidity) {
            this.temperature = temperature;
            this.pressure = pressure;
            this.humidity = humidity;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpenWeatherApiWeatherDesc {
        private String main;
        private String description;
        private String icon;

        public OpenWeatherApiWeatherDesc(@JsonProperty("main") String main,
                                         @JsonProperty("description") String description,
                                         @JsonProperty("icon") String icon) {
            this.main = main;
            this.description = description;
            this.icon = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpenWeatherApiWindDesc {
        private double speed;

        public OpenWeatherApiWindDesc(@JsonProperty("speed") double speed) {
            this.speed = speed;
        }
    }
}
