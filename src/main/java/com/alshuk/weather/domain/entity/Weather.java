package com.alshuk.weather.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    @Column(name = "date")
    private Timestamp date;

    @Column(name = "weather")
    private String weather;

    @Column(name = "icon")
    private String icon;

    @Column(name = "characteristic")
    private String characteristic;

    @Column(name = "temperature")
    private int temperature;

    @Column(name = "humidity")
    private int humidity;

    @Column(name = "pressure")
    private int pressure;

    @Column(name = "visibility")
    private double visibility;

    @Column(name = "wind_speed")
    private double windSpeed;

    @Column(name = "city_id")
    private int cityId;
}
