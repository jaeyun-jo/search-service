package com.searchservice.adapter.web.naver;

import com.searchservice.adapter.web.naver.request.NaverBlogSearchSortType;
import com.searchservice.adapter.web.naver.response.NaverBlogSearchResponse;
import com.searchservice.domain.blog.BlogSearchResult;
import com.searchservice.domain.blog.SearchBlogPort;
import com.searchservice.domain.blog.command.SearchBlogCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverSearchBlogAdapter implements SearchBlogPort {
    private final NaverSearchClient naverSearchClient;

    @Override
    public BlogSearchResult search(SearchBlogCommand command) {
        return NaverBlogSearchResponse.from(naverSearchClient.searchBlog(command.getQuery(),
                        command.getSize(),
                        command.getPage(),
                        NaverBlogSearchSortType.getSortType(command.getSort()).getQueryParam()),
                command.getSize());
    }
}
