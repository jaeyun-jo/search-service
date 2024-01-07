package com.searchservice.adapter.rdb.blog;

import com.searchservice.adapter.BlogSearchQuery;
import com.searchservice.adapter.RepositoryTestContext;
import com.searchservice.adapter.dto.PopularSearchQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BlogSearchQueryRepositoryTest extends RepositoryTestContext {

    @Test
    @DisplayName("블로그 검색어를 저장한다")
    void saveBlogSearchQueryTest() {
        // given
        // when
        blogSearchQueryRepository.saveAll(List.of(
                BlogSearchQuery.builder()
                        .query("쿼리1")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리2")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리2")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리2")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리3")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리3")
                        .build()
        ));

        // then
        long totalCount = blogSearchQueryRepository.count();
        List<BlogSearchQuery> query1Result = blogSearchQueryRepository.findByQuery("쿼리1");
        List<BlogSearchQuery> query2Result = blogSearchQueryRepository.findByQuery("쿼리2");
        List<BlogSearchQuery> query3Result = blogSearchQueryRepository.findByQuery("쿼리3");

        assertThat(totalCount).isEqualTo(6);
        assertThat(query1Result.size()).isEqualTo(1);
        assertThat(query2Result.size()).isEqualTo(3);
        assertThat(query3Result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("블로그 검색어를 많이 검색 된 순서로 조회한다")
    void findBlogSearchKeywordByCountTest() {
        // given
        blogSearchQueryRepository.saveAll(List.of(
                BlogSearchQuery.builder()
                        .query("쿼리1")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리2")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리2")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리2")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리3")
                        .build(),
                BlogSearchQuery.builder()
                        .query("쿼리3")
                        .build()
        ));

        // when
        List<PopularSearchQuery> result = blogSearchQueryRepository.findPopularSearchQueries(1);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getQuery()).isEqualTo("쿼리2");
        assertThat(result.get(0).getSearchCount()).isEqualTo(3);
    }
}
