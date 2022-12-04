package com.ssafy.home.estate.repository;

import com.ssafy.home.estate.entity.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubwayStationRepository extends JpaRepository<SubwayStation, Integer> {

    Optional<SubwayStation> findById(int id);

}