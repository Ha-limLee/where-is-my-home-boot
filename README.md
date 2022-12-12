# Take me home

## 0. 목차
1. 개요
2. 구체적인 기능
3. 기술스택

## 1. 개요
![image](https://user-images.githubusercontent.com/56950637/206682665-92fbdb48-c87e-499d-ae89-e499d3a88b22.png)

좋은 부동산을 구하기 위한 부동산 서비스입니다.

기간: 22.11.17(목) ~ 22.11.24(목)

인원: [이하림](https://github.com/Ha-limLee)(프론트엔드), [박종수](https://github.com/Bell-Water)(백엔드)


## 2. 구체적인 기능

### 2.1 주제별 뉴스
  #### 2.1.1 표시된 단어 클릭 -> 최신 뉴스표시
  ![image](https://user-images.githubusercontent.com/56950637/206700610-373ce635-925b-4d2f-b6d3-044fbc6173f1.png)

  화면에 표시한 단어를 클릭할 경우 해당 단어가 제목에 들어간 실시간 뉴스 목록을 조회합니다.
  뉴스는 화면에 1개씩 표시되며 일정시간이 지나면 해당 목록의 다음 뉴스로 자동으로 넘어갑니다.

### 2.2 게시판
  #### 2.2.1 글 목록 조회
  ![image](https://user-images.githubusercontent.com/56950637/207132701-07ae23ac-cf34-44ce-b9b9-739d96774108.png)

게시글은 세가지 종류(공지사항, 질문, 일반)가 있습니다. 옵션설정을 통해 특정 종류의 게시글 리스트만 조회할 수 있습니다.
admin 권한을 가진 유저가 글을 작성할 경우 작성자 옆에 (admin)표시가 붙습니다.

  #### 2.2.2 글 개별 조회
  ![image](https://user-images.githubusercontent.com/56950637/207132777-ef27a1c1-7f20-4a3b-80f5-be1056336d4e.png)

글 목록에서 게시글을 선택하면 해당 게시글에 대한 내용과 게시글에 달린 댓글 리스트를 볼 수 있습니다.
댓글도 글과 마찬가지로 admin유저가 작성한 댓글에는 작성자 옆에 (admin)표시가 붙도록 하였습니다.
  #### 2.2.3 글 생성
  ![image](https://user-images.githubusercontent.com/56950637/207132724-4c34b2c1-a61c-48df-8fb6-789a45b52ead.png)
  
로그인 한 유저만 접근이 가능하며 admin권한을 가진 유저만 '공지사항'이 종류에 표시됩니다.

  #### 2.2.4 글 삭제
  ![image](https://user-images.githubusercontent.com/56950637/207132757-733a5496-f968-415c-9bea-1622ea448e63.png)
해당 글을 작성한 유저만 글을 삭제할 수 있습니다. 삭제하기 전 alert창을 통해 확인을 요청합니다.

  #### 2.2.5 글 수정
  ![image](https://user-images.githubusercontent.com/56950637/207132739-651e5e0d-f99f-4d31-955c-0c7ee817efc7.png)

해당 글을 작성한 유저만 글을 수정할 수 있습니다.

  #### 2.2.6 댓글 생성
  ![image](https://user-images.githubusercontent.com/56950637/207133610-c8b25d64-e92c-46e7-945c-dd4aae5e254a.png)
  
  
댓글 생성,수정,삭제도 글과 같은 원리도 작동합니다.

  #### 2.2.7 댓글 수정
  ![image](https://user-images.githubusercontent.com/56950637/207133601-bd5d3885-0651-4989-a024-136bdf0ecba3.png)

  #### 2.2.8 댓글 삭제
  ![image](https://user-images.githubusercontent.com/56950637/207133912-0f115a0a-c55d-4650-a1cc-83d887a7f2e1.png)


### 2.3 회원정보
  회원가입, 회원정보 조회, 회원정보 수정, 회원정보 탈퇴, 로그인, 로그아웃
  #### 2.3.1 로그인, 로그아웃
  ![image](https://user-images.githubusercontent.com/56950637/207138893-0cf8327a-456a-43fb-8122-711d29a5830f.png)
  DB에 저장되 유저 정보를 바탕으로 로그인 로직이 작동합니다. Spring Security를 사용하여 로그인이 성공할 경우 access-token과 refresh-token을 header에 저장하여 유저에게 반환합니다.
  access-token이 만료된 경우 
  
### 2.4 아파트 매매 정보
  옵션으로 검색(시,군,구,년,월)
  아파트 마커 누르면 해당 아파트 전체 거래내역 표
  지도 아무데나 클릭시 해당 좌표 기준으로 조회

### 2.5 관심지역
  내 관심지역 목록 조회
  관심지역 클릭 -> 해당 지역 아파트들 마커 표시
  관심지역 추가, 삭제
  
### 2.6 검색
  건물 검색
  해당 건물 주변 아파트 검색
 


## 3. 기술스택

- 백엔드 
  - Spring Boot
  - Spring Security
  - Spring Data JPA
  - MyBatis
  - MySQL
  - Amazon RDS

- 프론트엔드
  - Vue.js
  - kakaomap api
  - bootstrap-vue
  
- 공통
  - Git&Github

