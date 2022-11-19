package com.ssafy.home.news.service;

import com.ssafy.home.news.dto.NewsDto;
import com.ssafy.home.news.dto.SearchNewsReq;
import com.ssafy.home.news.dto.SearchNewsRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class NewsService {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverSecret;

    @Value("${naver.url.search.news}")
    private String naverNewsSearchUrl;

    public List<NewsDto> searchNews(String keyword) {

        SearchNewsReq searchNewsReq = new SearchNewsReq();
        searchNewsReq.setQuery(keyword);

        URI uri = UriComponentsBuilder
                .fromUriString(naverNewsSearchUrl)
                .queryParams(searchNewsReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Naver-Client-Id", naverClientId);
        httpHeaders.set("X-Naver-Client-Secret", naverSecret);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ParameterizedTypeReference<SearchNewsRes> ptr = new ParameterizedTypeReference<SearchNewsRes>() {
        };


        ResponseEntity<SearchNewsRes> result = new RestTemplate()
                .exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        ptr
                );

        List<NewsDto> items = result.getBody().getItems();
        return items;
    }

}
