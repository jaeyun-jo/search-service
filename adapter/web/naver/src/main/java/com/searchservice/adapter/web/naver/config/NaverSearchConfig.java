package com.searchservice.adapter.web.naver.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverSearchConfig {

    @Bean
    public ErrorDecoder naverErrorDecoder() {
        return new NaverSearchApiClientErrorDecoder();
    }
}
