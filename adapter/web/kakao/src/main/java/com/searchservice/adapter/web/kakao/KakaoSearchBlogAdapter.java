package com.searchservice.adapter.web.kakao;

import com.searchservice.adapter.web.kakao.request.KakaoBlogSearchSortType;
import com.searchservice.adapter.web.kakao.response.KaKaoBlogSearchResponse;
import com.searchservice.domain.blog.BlogSearchResult;
import com.searchservice.domain.blog.SearchBlogPort;
import com.searchservice.domain.blog.command.SearchBlogCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoSearchBlogAdapter implements SearchBlogPort {
    private final KakaoSearchClient kakaoSearchClient;

    @Override
    public BlogSearchResult search(SearchBlogCommand command) {
        return KaKaoBlogSearchResponse.from(kakaoSearchClient.searchBlog(command.getQuery(),
                command.getPage(),
                command.getSize(),
                KakaoBlogSearchSortType.getSortType(command.getSort()).getQueryParam()));
    }
}
