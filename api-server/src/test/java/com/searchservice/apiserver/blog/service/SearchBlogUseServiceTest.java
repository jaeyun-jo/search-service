package com.searchservice.apiserver.blog.service;

import com.searchservice.adapter.web.kakao.exception.KakaoSearchInternalException;
import com.searchservice.apiserver.blog.request.BlogSearchSortType;
import com.searchservice.apiserver.blog.response.BlogSearchResponse;
import com.searchservice.domain.blog.BlogSearchResult;
import com.searchservice.domain.blog.SearchBlogPort;
import com.searchservice.domain.blog.UpdateBlogQueryPort;
import com.searchservice.domain.blog.command.SearchBlogCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SearchBlogUseServiceTest {

    @InjectMocks
    SearchBlogUseService sut;

    @Mock
    SearchBlogPort kakaoSearchBlogAdapter;

    @Mock
    SearchBlogPort naverSearchBlogAdapter;

    @Mock
    UpdateBlogQueryPort updateBlogQueryPort;


    @Test
    @DisplayName("블로그를 조회하면 카카오의 블로그 검색 결과를 내려준다")
    void returnKakaoSearchResult() {
        // given
        BlogSearchResult givenResult = BlogSearchResult.builder()
                .totalCount(1)
                .totalPage(1)
                .blogs(List.of(BlogSearchResult.Blog.builder()
                        .datetime(LocalDateTime.now())
                        .url("https://blog.naver.com/cdy1488/2233144413")
                        .blogname("흑곰의 블로그")
                        .contents("판교 최대의 맛집 이가네 양갈비는...")
                        .thumbnail("https://image.com")
                        .build()))
                .build();
        given(kakaoSearchBlogAdapter.search(any())).willReturn(givenResult);
        String query = "판교맛집";

        // when
        BlogSearchResponse result = sut.search(query, BlogSearchSortType.ACCURACY, 1, 10);

        // then
        BlogSearchResponse expected = BlogSearchResponse.builder()
                .totalCount(givenResult.getTotalCount())
                .totalPage(givenResult.getTotalPage())
                .blogs(givenResult.getBlogs().stream()
                        .map(blog -> BlogSearchResponse.Blog.builder()
                                .contents(blog.getContents())
                                .url(blog.getUrl())
                                .blogname(blog.getBlogname())
                                .datetime(blog.getDatetime())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(updateBlogQueryPort, times(1)).increaseSearchCount(query);
    }

    @Test
    @DisplayName("카카오에서 장애 발생시 네이버 블로그 검색 결과를 내려준다")
    void returnNaverSearchResult() {
        // given
        BlogSearchResult givenResult = BlogSearchResult.builder()
                .totalCount(1)
                .totalPage(1)
                .blogs(List.of(BlogSearchResult.Blog.builder()
                        .datetime(LocalDateTime.now())
                        .url("https://blog.naver.com/cdy1488/2233144413")
                        .blogname("흑곰의 블로그")
                        .contents("판교 최대의 맛집 이가네 양갈비는...")
                        .thumbnail("https://image.com")
                        .build()))
                .build();
        given(kakaoSearchBlogAdapter.search(any())).willThrow(new KakaoSearchInternalException());
        given(naverSearchBlogAdapter.search(any())).willReturn(givenResult);

        String query = "판교맛집";
        int page = 1;
        int size = 10;
        BlogSearchSortType sortType = BlogSearchSortType.ACCURACY;

        // when
        BlogSearchResponse result = sut.search(query, sortType, page, size);

        // then
        BlogSearchResponse expected = BlogSearchResponse.builder()
                .totalCount(givenResult.getTotalCount())
                .totalPage(givenResult.getTotalPage())
                .blogs(givenResult.getBlogs().stream()
                        .map(blog -> BlogSearchResponse.Blog.builder()
                                .contents(blog.getContents())
                                .url(blog.getUrl())
                                .blogname(blog.getBlogname())
                                .datetime(blog.getDatetime())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        SearchBlogCommand expectedCommand = SearchBlogCommand.builder()
                .query(query)
                .sort(SearchBlogCommand.SortType.ACCURACY)
                .page(page)
                .size(size)
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(updateBlogQueryPort).increaseSearchCount(query);

        ArgumentCaptor<SearchBlogCommand> argumentCaptor = ArgumentCaptor.forClass(SearchBlogCommand.class);
        verify(naverSearchBlogAdapter).search(argumentCaptor.capture());
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedCommand);
    }
}
