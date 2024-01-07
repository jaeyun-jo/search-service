package com.searchservice.domain.blog;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BlogPopularSearchQuery {
    private final String query;
    private final long count;

    @Builder
    public BlogPopularSearchQuery(String query, long count) {
        this.query = query;
        this.count = count;
    }
}
