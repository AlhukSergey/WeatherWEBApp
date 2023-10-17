package com.alshuk.weather.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodWeatherSearchForm {
    @NotBlank(message = "The field must be filled in!")
    @Size(min = 2, message = "At least two characters!")
    private String name;
    @Past
    private Date from;
    @Past
    private Date to;
}
