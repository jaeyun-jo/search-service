package com.searchservice.adapter;

import com.searchservice.domain.blog.BlogPopularSearchQuery;
import com.searchservice.domain.blog.ReadBlogPopularSearchQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReadPopularSearchQueryAdapter implements ReadBlogPopularSearchQueryPort {
    private final BlogSearchQueryRepository blogSearchQueryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BlogPopularSearchQuery> readPopularSearchQuery(int limit) {
        return blogSearchQueryRepository.findPopularSearchQueries(limit)
                .stream()
                .map(blogSearchQuery -> BlogPopularSearchQuery.builder()
                        .query(blogSearchQuery.getQuery())
                        .count(blogSearchQuery.getSearchCount())
                        .build())
                .collect(Collectors.toList());
    }
}
