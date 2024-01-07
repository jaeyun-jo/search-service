package com.searchservice.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class RepositoryTestContext {

    @Autowired
    protected BlogSearchQueryRepository blogSearchQueryRepository;

    @BeforeEach
    void init() {
        blogSearchQueryRepository.deleteAll();
    }
}
