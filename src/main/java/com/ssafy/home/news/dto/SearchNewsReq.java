package com.ssafy.home.news.dto;

import com.ssafy.home.common.NewsProperties;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
public class SearchNewsReq {

    private String query="";
    private int display = NewsProperties.DISPLAY;
    private int start = NewsProperties.START;
    private String sort = "sim";

    public MultiValueMap<String,String> toMultiValueMap() {
        LinkedMultiValueMap map = new LinkedMultiValueMap();

        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);

        return map;
    }
}
