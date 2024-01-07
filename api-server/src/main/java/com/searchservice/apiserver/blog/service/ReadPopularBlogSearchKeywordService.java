package com.searchservice.apiserver.blog.service;

import com.searchservice.apiserver.blog.response.BlogPopularSearchKeywordResponse;
import com.searchservice.domain.blog.ReadBlogPopularSearchQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadPopularBlogSearchKeywordService {
    private final ReadBlogPopularSearchQueryPort readBlogPopularSearchQueryPort;

    public BlogPopularSearchKeywordResponse readPopularKeywords() {
        return BlogPopularSearchKeywordResponse.builder()
                .popularKeywords(readBlogPopularSearchQueryPort.readPopularSearchQuery(10)
                        .stream()
                        .map(BlogPopularSearchKeywordResponse.PopularKeyword::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
