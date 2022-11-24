package com.ssafy.home.estate.repository;

import com.ssafy.home.estate.entity.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BusStationRepository extends JpaRepository<BusStation, Integer> {

    Optional<BusStation> findById(int id);
}