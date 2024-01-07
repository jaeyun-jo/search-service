package com.searchservice.adapter;

import com.searchservice.adapter.dto.PopularSearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogSearchQueryRepository extends JpaRepository<BlogSearchQuery, String> {

    // TODO:: native query 제거
    @Query(value = "SELECT blogSearch.query as query, COUNT(blogSearch.query) as searchCount " +
            "FROM blog_search_query blogSearch GROUP BY blogSearch.query ORDER BY COUNT(blogSearch.query) DESC LIMIT :limit", nativeQuery = true)
    List<PopularSearchQuery> findPopularSearchQueries(int limit);

    List<BlogSearchQuery> findByQuery(String query);
}
