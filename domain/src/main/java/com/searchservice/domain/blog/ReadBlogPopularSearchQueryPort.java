package com.searchservice.domain.blog;

import java.util.List;

public interface ReadBlogPopularSearchQueryPort {
    List<BlogPopularSearchQuery> readPopularSearchQuery(int limit);
}
