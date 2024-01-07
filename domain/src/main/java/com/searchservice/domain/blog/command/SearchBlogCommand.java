package com.searchservice.domain.blog.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchBlogCommand {
    private final String query;
    private final SortType sort;
    private final int page;
    private final int size;

    @Builder
    public SearchBlogCommand(String query, SortType sort, int page, int size) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }

    public enum SortType {
        ACCURACY, RECENCY
    }
}
