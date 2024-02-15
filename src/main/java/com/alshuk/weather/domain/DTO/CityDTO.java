package com.alshuk.weather.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDTO {
    @NotBlank(message = "The field must be filled in!")
    @Size(min = 2, message = "At least two characters!")
    private String name;
    private String fr;
    private String ru;
}
