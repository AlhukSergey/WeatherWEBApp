package com.alshuk.weather.enums;

import lombok.Getter;

@Getter
public enum PagesPathEnum {
    HOME_PAGE("home"),
    CURRENT_INFO_PAGE("current-info"),
    HISTORY_INFO_PAGE("history-info"),
    ERROR_PAGE("error");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

}