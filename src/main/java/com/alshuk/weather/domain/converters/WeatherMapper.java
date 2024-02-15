package com.alshuk.weather.domain.converters;

import com.alshuk.weather.domain.DTO.WeatherDTO;
import com.alshuk.weather.domain.entity.Weather;
import com.alshuk.weather.domain.model.WeatherModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = WeatherMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WeatherMapper {

    Weather toEntity(WeatherModel weatherModel);

    Weather toEntity(WeatherDTO weatherDTO);

    WeatherModel toModel(Weather weather);

    WeatherModel toModel(WeatherDTO weatherDTO);

    WeatherDTO toDTO(Weather weather);

    WeatherDTO toDTO(WeatherModel weatherModel);
}
