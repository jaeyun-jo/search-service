package com.searchservice.adapter.web.kakao.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.searchservice.domain.blog.BlogSearchResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class KaKaoBlogSearchResponse {
    private static final DateTimeFormatter POSTDATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private final Meta meta;
    private final List<SearchContent> documents;

    @Builder
    public KaKaoBlogSearchResponse(Meta meta, List<SearchContent> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    @Getter
    public static class Meta {
        @JsonProperty("total_count")
        private final long totalCount;

        @JsonProperty("pageable_count")
        private final int pageableCount;

        @JsonProperty("is_end")
        private final boolean end;

        @Builder
        public Meta(long totalCount, int pageableCount, boolean end) {
            this.totalCount = totalCount;
            this.pageableCount = pageableCount;
            this.end = end;
        }
    }

    @Getter
    public static class SearchContent {
        private final String contents;
        private final String url;
        private final String blogname;
        private final String thumbnail;
        private final String datetime;

        @Builder
        public SearchContent(String contents, String url, String blogname, String thumbnail, String datetime) {
            this.contents = contents;
            this.url = url;
            this.blogname = blogname;
            this.thumbnail = thumbnail;
            this.datetime = datetime;
        }
    }

    public static BlogSearchResult from(KaKaoBlogSearchResponse response) {
        return BlogSearchResult.builder()
                .totalCount(response.getMeta().getTotalCount())
                .totalPage(response.getMeta().getPageableCount())
                .blogs(response.getDocuments().stream()
                        .map(content -> BlogSearchResult.Blog.builder()
                                .contents(content.getContents())
                                .url(content.getUrl())
                                .blogname(content.getBlogname())
                                .thumbnail(content.getThumbnail())
                                .datetime(LocalDateTime.parse(content.getDatetime(), POSTDATE_FORMAT))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
