package com.searchservice.adapter.web.kakao;

import com.searchservice.adapter.web.kakao.config.KakaoSearchConfig;
import com.searchservice.adapter.web.kakao.response.KaKaoBlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao", url = "${hosts.kakao.kakao-search-api.url}", configuration = KakaoSearchConfig.class)
public interface KakaoSearchClient {

    @GetMapping("/v2/search/blog")
    KaKaoBlogSearchResponse searchBlog(@RequestParam String query, @RequestParam int page, @RequestParam int size, @RequestParam String sort);
}
