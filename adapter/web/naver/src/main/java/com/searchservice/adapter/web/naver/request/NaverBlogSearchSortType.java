package com.searchservice.adapter.web.naver.request;

import com.searchservice.domain.blog.command.SearchBlogCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum NaverBlogSearchSortType {
    ACCURACY("sim",SearchBlogCommand.SortType.ACCURACY),
    RECENCY("date", SearchBlogCommand.SortType.RECENCY);

    private final String queryParam;
    private final SearchBlogCommand.SortType domainSortType;

    public static NaverBlogSearchSortType getSortType(SearchBlogCommand.SortType domainSortType) {
        return Arrays.stream(NaverBlogSearchSortType.values())
                .filter(sortType -> sortType.getDomainSortType().equals(domainSortType))
                .findAny()
                .orElse(ACCURACY);
    }
}
