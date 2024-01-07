package com.searchservice.apiserver.blog.request;

import com.searchservice.domain.blog.command.SearchBlogCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlogSearchSortType {
    ACCURACY(SearchBlogCommand.SortType.ACCURACY),
    RECENCY(SearchBlogCommand.SortType.RECENCY);

    private final SearchBlogCommand.SortType domainSortType;
}
