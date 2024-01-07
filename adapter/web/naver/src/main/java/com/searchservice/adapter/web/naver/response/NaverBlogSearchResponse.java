package com.searchservice.adapter.web.naver.response;

import com.searchservice.domain.blog.BlogSearchResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NaverBlogSearchResponse {
    private static final DateTimeFormatter POSTDATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final long total;
    private final long start;
    private final int display;
    private final List<SearchContent> items;

    @Builder
    public NaverBlogSearchResponse(long total, long start, int display, List<SearchContent> items) {
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }

    @Getter
    public static class SearchContent {
        private final String link;
        private final String description;
        private final String bloggername;
        private final String postdate;

        @Builder
        public SearchContent(String link, String description, String bloggername, String postdate) {
            this.link = link;
            this.description = description;
            this.bloggername = bloggername;
            this.postdate = postdate;
        }
    }

    public static BlogSearchResult from(NaverBlogSearchResponse response, int size) {
        return BlogSearchResult.builder()
                .totalCount(response.getTotal())
                .totalPage(response.getTotal() > 0 ? (int) (response.getTotal() / size) + 1 : 0)
                .blogs(response.getItems().stream()
                        .map(content -> BlogSearchResult.Blog.builder()
                                .contents(content.getDescription())
                                .url(content.getLink())
                                .blogname(content.getBloggername())
                                .datetime(LocalDate.parse(content.getPostdate(), POSTDATE_FORMAT).atStartOfDay())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
