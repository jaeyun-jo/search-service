package com.searchservice.adapter.web.kakao.request;

import com.searchservice.domain.blog.command.SearchBlogCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum KakaoBlogSearchSortType {
    ACCURACY("accuracy",SearchBlogCommand.SortType.ACCURACY),
    RECENCY("recency", SearchBlogCommand.SortType.RECENCY);

    private final String queryParam;
    private final SearchBlogCommand.SortType domainSortType;

    public static KakaoBlogSearchSortType getSortType(SearchBlogCommand.SortType domainSortType) {
        return Arrays.stream(KakaoBlogSearchSortType.values())
                .filter(sortType -> sortType.getDomainSortType().equals(domainSortType))
                .findAny()
                .orElse(ACCURACY);
    }
}
