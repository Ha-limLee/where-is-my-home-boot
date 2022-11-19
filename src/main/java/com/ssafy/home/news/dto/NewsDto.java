package com.ssafy.home.news.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewsDto {

    private String title;  // 뉴스 기사의 제목
    private String originallink;  // 뉴스 기사 원문의 URL
    private String link; // 뉴스 기사의 네이버 뉴스 URL. 네이버에 제공되지 않은 기사라면 기사 원문의 URL을 반환합니다.
    private String description;  // 뉴스 기사의 내용을 요약한 패시지 정보. 패시지 정보에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
    private Date pubDate; // 뉴스 기사가 네이버에 제공된 시간. 네이버에 제공되지 않은 기사라면 기사 원문이 제공된 시간을 반환합니다.

}
