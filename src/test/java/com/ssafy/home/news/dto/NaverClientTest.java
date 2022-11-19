package com.ssafy.home.news.dto;

import com.ssafy.home.news.service.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NaverClientTest {

    @Autowired
    private NewsService naverClient;

    @Test
    void localSearch() {

//        SearchNewsReq searchNewsReq = new SearchNewsReq();
//        searchNewsReq.setQuery("부동산");
        String keyword = "부동산";

        List<NewsDto> items = naverClient.searchNews(keyword);

        for(NewsDto nd : items) {
            System.out.println(nd);
        }

    }
}