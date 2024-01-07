package com.searchservice.adapter.web.kakao;

import com.searchservice.adapter.web.kakao.response.KaKaoBlogSearchResponse;
import com.searchservice.domain.blog.BlogSearchResult;
import com.searchservice.domain.blog.command.SearchBlogCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class KakaoSearchBlogAdapterTest {

    @InjectMocks
    KakaoSearchBlogAdapter sut;

    @Mock
    KakaoSearchClient kakaoSearchClient;

    @Test
    @DisplayName("카카오에서 블로그를 검색한다")
    void returnSearchResponseTest() {
        // given
        KaKaoBlogSearchResponse givenResult = KaKaoBlogSearchResponse.builder()
                .documents(List.of(
                        KaKaoBlogSearchResponse.SearchContent.builder()
                                .blogname("blogname")
                                .contents("contents")
                                .url("url")
                                .datetime("2020-01-01T00:00:00.000+09:00")
                                .build()
                ))
                .meta(KaKaoBlogSearchResponse.Meta.builder()
                        .pageableCount(10)
                        .totalCount(100)
                        .build())
                .build();
        given(kakaoSearchClient.searchBlog(any(), anyInt(), anyInt(), any()))
                .willReturn(givenResult);
        SearchBlogCommand command = SearchBlogCommand.builder()
                .sort(SearchBlogCommand.SortType.ACCURACY)
                .size(10)
                .page(1)
                .build();
        // when

        BlogSearchResult result = sut.search(command);

        // then
        BlogSearchResult expected = BlogSearchResult.builder()
                .totalPage(givenResult.getMeta().getPageableCount())
                .totalCount(givenResult.getMeta().getTotalCount())
                .blogs(givenResult.getDocuments().stream()
                        .map(content -> BlogSearchResult.Blog.builder()
                                .contents(content.getContents())
                                .blogname(content.getBlogname())
                                .url(content.getUrl())
                                .datetime(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                                .build())
                        .collect(Collectors.toList()))
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
