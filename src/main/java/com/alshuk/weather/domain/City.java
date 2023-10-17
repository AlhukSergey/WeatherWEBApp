package com.alshuk.weather.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @NotBlank(message = "The field must be filled in!")
    @Size(min = 2, message = "At least two characters!")
    private String name;
}
