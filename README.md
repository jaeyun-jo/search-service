# search-service

# Executable Jar

# API 문서
## 블로그 검색

### 기본정보
| 메서드  | url                               |
|------|-----------------------------------|
| GET  | http://localhost:8080/v1/search/blog |

### 쿼리 파라미터
| 이름    | 타입      | 설명                                                  | 필수  |
|-------|---------|-----------------------------------------------------|-----|
| query | String  | 검색 질의어                                              | O   |
| sort  | String  | 정확도순 (default) - ACCURACY, <br/> 최신순 - RECENCY<br/> | X   |
| page  | Integer | 결과 페이지 번호                                           | X   |
| size  | Integer | 페이지당 문서 수(1-50 사이의 값. default 10)                   | X   |


### 응답
| 이름                    | 타입      | 설명        |
|-----------------------|---------|-----------|
| status                | String  | 서버 응답 상태  |
| message               | String  | 메세지       |
| serverDatetime        | String  | 서버 응답 시간  |
| data                  | Data[]  | 응답 결과     |
| data.totalCount       | Integer | 검색된 문서 수  |
| data.totalPage        | Integer | 페이지       |
| data.blogs            | Blog[]  | 블로그 정보    |
| data.blogs[].blogname | String  | 블로그명      |
| data.blogs[].link     | String  | 블로그 글 URL |
| data.blogs[].contents | String  | 블로그 글 요약  |
| data.blogs[].datetime | String  | 블로그 작성시간  |

### 예제
요청
```
curl -X GET "http://localhost:8080/v1/search/blog?query=christmas&sort=RECENCY&page=1&size=2" -H "accept: application/json"
```

응답
```
{
    "status": "SUCCESS",
    "message": "성공",
    "serverDatetime": "2024-01-06 21:52:30",
    "data": {
        "totalCount": 639457,
        "totalPage": 800,
        "blogs": [
            {
                "contents": "선물까지 받으니 더 맘이 따뜻해지는, 더 열심히 살고 누구든 진심으로 대해야겠다고 생각했던 날🎄 자기 웃긴사진이라고 막 보내지만 그래도 사랑스러운 내덩생 🖤 <b>Christmas</b> Eve 🎄 우리반 클레스 메이트, 유니어의 친구들 그리고 새로 알게된 일본인 친구까지 이 친구들의 좋은 에너지와 따뜻함까지 얻었던 행복한 이브...",
                "url": "https://evenhajim.tistory.com/467",
                "blogname": "forest_eun",
                "datetime": "2023-12-26"
            },
            {
                "contents": "[미국지식] 크리스마스 푸드 Top10, <b>Christmas</b> Food 크리스마스 뉴욕시티 (source: youtube.com) [미국지식] 크리스마스 푸드 Top10, <b>Christmas</b> Food 에 대해 리뷰하려고 합니다. 크리스마스에 미국이나 유럽 사람들은 주로 어떤 요리나 특식을 먹는지 궁금해서 정리해 보았네요. 미국이 이민사회로 백인들이 주류이다...",
                "url": "https://stephan-review.tistory.com/1949",
                "blogname": "S부장 미국이민 적응일지",
                "datetime": "2023-12-20"
            }
        ]
    }
}
```



## 인기 검색어 목록 조회
가장 많이 검색 된 10개의 검색어 목록을 조회합니다.

### 기본정보
| 메서드  | url                               |
|------|-----------------------------------|
| GET  | http://localhost:8080/v1/blogs/popular-keyword |

### 응답
| 이름                       | 타입      | 설명        |
|--------------------------|---------|-----------|
| status                   | String  | 서버 응답 상태  |
| message                  | String  | 메세지       |
| serverDatetime           | String  | 서버 응답 시간  |
| data                     | Data[]  | 응답 결과     |
| data.popularKeywords     | Blog[]  | 인기 검색어 정보 |
| data.blogs[].keyword     | String  | 키워드       |
| data.blogs[].searchCount | String  | 검색 횟수     |

### 예제
요청
```
curl -X GET "http://localhost:8080/v1/blogs/popular-keyword"
```

응답
```
{
    "status": "SUCCESS",
    "message": "성공",
    "serverDatetime": "2024-01-06 22:11:37",
    "data": {
        "popularKeywords": [
            {
                "keyword": "판교맛집",
                "searchCount": 19
            },
            {
                "keyword": "신작게임",
                "searchCount": 5
            },
            {
                "keyword": "원두",
                "searchCount": 3
            },
            {
                "keyword": "christmas",
                "searchCount": 2
            },
            {
                "keyword": "분리수거",
                "searchCount": 1
            },
            {
                "keyword": "카페",
                "searchCount": 1
            }
        ]
    }
}
```
