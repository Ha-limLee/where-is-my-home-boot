package com.ssafy.home.board.repository;

import com.ssafy.home.board.entity.Article;
import com.ssafy.home.board.entity.ArticleProp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Page<Article> findAll(Pageable pageable);

    Page<Article> findByArticleProp(ArticleProp ap, Pageable pageable);
    
    Page<Article> findByArticlePropIn(Collection<ArticleProp> ap, Pageable pageable);
}