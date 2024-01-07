package com.searchservice.adapter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "blog_search_query",
        indexes = {
                @Index(name = "idx_query", columnList = "query")
        }
)
@Getter
@NoArgsConstructor
public class BlogSearchQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String query;

    @Builder
    public BlogSearchQuery(String query) {
        this.query = query;
        this.id = UUID.randomUUID().toString();
    }
}
