package com.searchservice.domain.blog;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BlogSearchResult {
    private final long totalCount;
    private final long totalPage;
    private final List<Blog> blogs;

    @Builder
    public BlogSearchResult(long totalCount, long totalPage, List<Blog> blogs) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.blogs = blogs;
    }

    @Getter
    public static class Blog {
        private final String contents;
        private final String url;
        private final String blogname;
        private final String thumbnail;
        private final LocalDateTime datetime;

        @Builder
        public Blog(String contents, String url, String blogname, String thumbnail, LocalDateTime datetime) {
            this.contents = contents;
            this.url = url;
            this.blogname = blogname;
            this.thumbnail = thumbnail;
            this.datetime = datetime;
        }
    }
}
