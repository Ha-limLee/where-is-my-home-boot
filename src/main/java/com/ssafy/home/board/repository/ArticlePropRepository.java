package com.ssafy.home.board.repository;

import com.ssafy.home.board.entity.ArticleProp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticlePropRepository extends JpaRepository<ArticleProp, Integer> {
    Optional<ArticleProp> findByPropName(String name);
}