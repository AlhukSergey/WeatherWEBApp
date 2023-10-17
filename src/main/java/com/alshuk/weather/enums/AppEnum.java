package com.alshuk.weather.enums;

import lombok.Getter;

@Getter
public enum AppEnum {
    OPEN_WEATHER_API_KEY("2956954813159d2e86d9208190adcef1"),
    VISUAL_CROSSING_API_KEY("JH6SKYHGYC27JK8SLWQNGWKXG");

    private final String key;

    AppEnum(String key) {
        this.key = key;
    }
}
