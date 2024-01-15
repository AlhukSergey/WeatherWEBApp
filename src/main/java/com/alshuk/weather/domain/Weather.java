package com.alshuk.weather.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@Table(name = "weather")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Weather extends BaseEntity {
    @NotNull
    @Column(name = "date")
    private Timestamp date;

    @NotNull
    @Column(name = "weather")
    private String weather;

    @NotNull
    @Column(name = "icon")
    private String icon;

    @NotNull
    @Column(name = "characteristic")
    private String characteristic;

    @NotNull
    @Column(name = "temperature")
    private int temperature;

    @NotNull
    @Column(name = "humidity")
    private int humidity;

    @NotNull
    @Column(name = "pressure")
    private int pressure;

    @NotNull
    @Column(name = "visibility")
    private double visibility;

    @NotNull
    @Column(name = "wind_speed")
    private double windSpeed;

    @NotNull
    @Column(name = "city_id")
    private int cityId;
}
