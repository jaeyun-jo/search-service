package com.searchservice.adapter.web.naver;

import com.searchservice.adapter.web.naver.response.NaverBlogSearchResponse;
import com.searchservice.domain.blog.BlogSearchResult;
import com.searchservice.domain.blog.command.SearchBlogCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class NaverSearchBlogAdapterTest {

    @InjectMocks
    NaverSearchBlogAdapter sut;

    @Mock
    NaverSearchClient naverSearchClient;

    @Test
    @DisplayName("네이버에서 블로그를 검색한다")
    void returnSearchResponseTest() {
        // given
        LocalDate postDate = LocalDate.now();
        NaverBlogSearchResponse givenResult = NaverBlogSearchResponse.builder()
                .items(List.of(
                        NaverBlogSearchResponse.SearchContent.builder()
                                .bloggername("blogname")
                                .description("contents")
                                .link("url")
                                .postdate(postDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                                .build()
                ))
                .total(100)
                .display(1)
                .start(1)
                .build();
        given(naverSearchClient.searchBlog(any(), anyInt(), anyInt(), any()))
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
                .totalPage((givenResult.getTotal() / command.getSize()) + 1)
                .totalCount(givenResult.getTotal())
                .blogs(givenResult.getItems().stream()
                        .map(content -> BlogSearchResult.Blog.builder()
                                .contents(content.getDescription())
                                .blogname(content.getBloggername())
                                .url(content.getLink())
                                .datetime(postDate.atStartOfDay())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
