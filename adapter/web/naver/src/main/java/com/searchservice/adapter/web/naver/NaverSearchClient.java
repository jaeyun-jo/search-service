package com.searchservice.adapter.web.naver;

import com.searchservice.adapter.web.naver.config.NaverSearchConfig;
import com.searchservice.adapter.web.naver.response.NaverBlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver", url = "${hosts.naver.naver-search-api.url}", configuration = NaverSearchConfig.class)
public interface NaverSearchClient {

    @GetMapping("/v1/search/blog.json")
    NaverBlogSearchResponse searchBlog(@RequestParam String query,
                                       @RequestParam int display,
                                       @RequestParam int start,
                                       @RequestParam String sort);
}
