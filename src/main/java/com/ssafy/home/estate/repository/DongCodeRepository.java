package com.ssafy.home.estate.repository;

import com.ssafy.home.estate.entity.DongCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface DongCodeRepository extends JpaRepository<DongCode, String> {

    List<DongCode> findByDongCodeIn(List<String> list);
}
