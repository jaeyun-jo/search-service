package com.searchservice.apiserver.blog.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.searchservice.domain.blog.BlogSearchResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BlogSearchResponse {
    private final long totalCount;
    private final long totalPage;
    private final List<Blog> blogs;

    @Builder
    public BlogSearchResponse(long totalCount, long totalPage, List<Blog> blogs) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.blogs = blogs;
    }

    @Getter
    public static class Blog {
        private final String contents;
        private final String url;
        private final String blogname;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDateTime datetime;

        @Builder
        public Blog(String contents, String url, String blogname, LocalDateTime datetime) {
            this.contents = contents;
            this.url = url;
            this.blogname = blogname;
            this.datetime = datetime;
        }
    }

    public static BlogSearchResponse from(BlogSearchResult result) {
        return BlogSearchResponse.builder()
                .totalCount(result.getTotalCount())
                .totalPage(result.getTotalPage())
                .blogs(result.getBlogs().stream()
                        .map(blog -> Blog.builder()
                                .contents(blog.getContents())
                                .url(blog.getUrl())
                                .blogname(blog.getBlogname())
                                .datetime(blog.getDatetime())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
