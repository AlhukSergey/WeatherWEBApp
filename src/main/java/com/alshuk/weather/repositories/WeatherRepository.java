package com.alshuk.weather.repositories;

import com.alshuk.weather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    @Query("SELECT AVG(temperature) FROM Weather WHERE cityId = ?1 AND date BETWEEN ?2 AND ?3")
    Double searchAverageTemp(int id, Timestamp from, Timestamp To);
}
