package com.alshuk.weather.repositories;

import com.alshuk.weather.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Query("SELECT c FROM City c WHERE :name in (name, fr, ru)")
    City searchByAnyLanguageName(@Param("name") String cityName);
}
