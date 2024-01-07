package com.searchservice.apiserver.blog.service;

import com.searchservice.apiserver.blog.response.BlogPopularSearchKeywordResponse;
import com.searchservice.domain.blog.BlogPopularSearchQuery;
import com.searchservice.domain.blog.ReadBlogPopularSearchQueryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ReadPopularBlogSearchKeywordServiceTest {

    @InjectMocks
    ReadPopularBlogSearchKeywordService sut;

    @Mock
    ReadBlogPopularSearchQueryPort readBlogPopularSearchQueryPort;

    @Test
    @DisplayName("인기 검색어를 조회한다")
    void returnPopularKeywords() {
        // given
        List<BlogPopularSearchQuery> givenKeywords = List.of(
                BlogPopularSearchQuery.builder()
                        .query("판교맛집")
                        .count(342)
                        .build(),
                BlogPopularSearchQuery.builder()
                        .query("피자만들기")
                        .count(10)
                        .build());
        given(readBlogPopularSearchQueryPort.readPopularSearchQuery(anyInt())).willReturn(givenKeywords);

        // when
        BlogPopularSearchKeywordResponse result = sut.readPopularKeywords();

        // then
        List<BlogPopularSearchKeywordResponse.PopularKeyword> expected = givenKeywords.stream()
                .map(keyword -> BlogPopularSearchKeywordResponse.PopularKeyword.builder()
                        .keyword(keyword.getQuery())
                        .searchCount(keyword.getCount())
                        .build())
                .collect(Collectors.toList());
        assertThat(result.getPopularKeywords()).usingRecursiveComparison().isEqualTo(expected);
    }
}
