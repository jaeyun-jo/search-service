package com.searchservice.apiserver.blog.service;

import com.searchservice.adapter.web.kakao.exception.KakaoSearchInternalException;
import com.searchservice.apiserver.blog.request.BlogSearchSortType;
import com.searchservice.apiserver.blog.response.BlogSearchResponse;
import com.searchservice.domain.blog.BlogSearchResult;
import com.searchservice.domain.blog.SearchBlogPort;
import com.searchservice.domain.blog.UpdateBlogQueryPort;
import com.searchservice.domain.blog.command.SearchBlogCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchBlogUseService {
    private final SearchBlogPort kakaoSearchBlogAdapter;
    private final SearchBlogPort naverSearchBlogAdapter;
    private final UpdateBlogQueryPort updateBlogQueryPort;

    public BlogSearchResponse search(String query, BlogSearchSortType sortType, int page, int size) {
        BlogSearchResult result = getSearchResponse(SearchBlogCommand.builder()
                .query(query)
                .sort(sortType.getDomainSortType())
                .page(page)
                .size(size)
                .build());
        updateBlogQueryPort.increaseSearchCount(query);
        return BlogSearchResponse.from(result);
    }

    private BlogSearchResult getSearchResponse(SearchBlogCommand command) {
        try {
            return kakaoSearchBlogAdapter.search(command);
        } catch (KakaoSearchInternalException e) {
            return naverSearchBlogAdapter.search(command);
        }
    }
}
