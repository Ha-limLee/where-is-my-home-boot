package com.ssafy.home.news.controller;

import com.ssafy.home.news.dto.NewsDto;
import com.ssafy.home.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/naver")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/news/{keyword}")
    public ResponseEntity<?> getNewsListByKeyword(@PathVariable String keyword) {

        List<NewsDto> newsList = newsService.searchNews(keyword);
        return ResponseEntity.ok(newsList);
    }

}
