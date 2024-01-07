package com.searchservice.domain.blog;

import com.searchservice.domain.blog.command.SearchBlogCommand;

public interface SearchBlogPort {
    BlogSearchResult search(SearchBlogCommand command);
}
