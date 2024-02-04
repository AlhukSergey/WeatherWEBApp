package com.alshuk.weather.repositories;

import com.alshuk.weather.domain.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    @Query("SELECT AVG(temperature) FROM Weather WHERE cityId = ?1 AND date BETWEEN ?2 AND ?3")
    Double searchAverageTemp(int id, Timestamp from, Timestamp To);

    @Query(nativeQuery = true, value = "SELECT * FROM Weather w WHERE w.city_id = :cityId ORDER BY date DESC LIMIT 1")
    Optional<Weather> findLastCityWeather(@Param("cityId") int cityId);
}
