package com.searchservice.apiserver.blog.controller;

import com.searchservice.apiserver.blog.request.BlogSearchSortType;
import com.searchservice.apiserver.blog.response.BlogPopularSearchKeywordResponse;
import com.searchservice.apiserver.blog.response.BlogSearchResponse;
import com.searchservice.apiserver.blog.service.ReadPopularBlogSearchKeywordService;
import com.searchservice.apiserver.blog.service.SearchBlogUseService;
import com.searchservice.apiserver.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequiredArgsConstructor
public class BlogSearchController {
    private final SearchBlogUseService searchBlogUseService;
    private final ReadPopularBlogSearchKeywordService readPopularBlogSearchKeywrodUseCase;

    @GetMapping("/v1/search/blog")
    public CommonResponse<BlogSearchResponse> searchBlog(@RequestParam @NotBlank String query,
                                                         @RequestParam(required = false, defaultValue = "ACCURACY") BlogSearchSortType sortType,
                                                         @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
                                                         @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 50) int size) {
        return CommonResponse.success(searchBlogUseService.search(query.trim(), sortType, page, size));
    }

    @GetMapping("/v1/blogs/popular-keyword")
    public CommonResponse<BlogPopularSearchKeywordResponse> readPopularKeyword() {
        return CommonResponse.success(readPopularBlogSearchKeywrodUseCase.readPopularKeywords());
    }
}
