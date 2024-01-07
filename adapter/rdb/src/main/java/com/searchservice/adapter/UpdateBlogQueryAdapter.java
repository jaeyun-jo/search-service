package com.searchservice.adapter;

import com.searchservice.domain.blog.UpdateBlogQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class UpdateBlogQueryAdapter implements UpdateBlogQueryPort {
    private final BlogSearchQueryRepository blogSearchQueryRepository;

    @Transactional
    @Override
    public void increaseSearchCount(String query) {
        blogSearchQueryRepository.save(BlogSearchQuery.builder().query(query).build());
    }
}
