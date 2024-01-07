package com.searchservice.apiserver.blog.response;

import com.searchservice.domain.blog.BlogPopularSearchQuery;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BlogPopularSearchKeywordResponse {

    private final List<PopularKeyword> popularKeywords;

    @Builder
    public BlogPopularSearchKeywordResponse(List<PopularKeyword> popularKeywords) {
        this.popularKeywords = popularKeywords;
    }

    @Getter
    public static class PopularKeyword {
        private final String keyword;
        private final long searchCount;

        @Builder
        public PopularKeyword(String keyword, long searchCount) {
            this.keyword = keyword;
            this.searchCount = searchCount;
        }

        public static BlogPopularSearchKeywordResponse.PopularKeyword from(BlogPopularSearchQuery query) {
            return BlogPopularSearchKeywordResponse.PopularKeyword.builder()
                    .keyword(query.getQuery())
                    .searchCount(query.getCount())
                    .build();
        }
    }
}
