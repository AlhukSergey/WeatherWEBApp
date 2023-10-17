package com.alshuk.weather.enums;

import lombok.Getter;

@Getter
public enum RequestParamEnum {
    CITY_NAME("city_name"),
    WEATHER("weather"),
    ICON("icon"),
    TEMPERATURE("temperature"),
    CHARACTERISTIC("characteristic"),
    HUMIDITY("humidity"),
    PRESSURE("pressure"),
    VISIBILITY("visibility"),
    WIND_SPEED("wind_speed"),
    ERROR("error"),
    AVERAGE_TEMPERATURE("average_temperature");

    private final String value;

    RequestParamEnum(String value) {
        this.value = value;
    }
}
