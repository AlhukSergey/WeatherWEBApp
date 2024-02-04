package com.alshuk.weather.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherSearchForm {
    @NotBlank(message = "The field must be filled in!")
    @Size(min = 2, message = "At least two characters!")
    private String name;
    @NotNull(message = "The field must be filled in!")
    @PastOrPresent
    private LocalDate from;
    @NotNull(message = "The field must be filled in!")
    @PastOrPresent
    private LocalDate to;
}
