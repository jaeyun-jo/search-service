package com.searchservice.adapter.web.kakao.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoSearchConfig {

    @Bean
    public ErrorDecoder kakaoErrorDecoder() {
        return new KakaoSearchApiClientErrorDecoder();
    }
}
