package com.searchservice.adapter.web.kakao.config;

import com.searchservice.adapter.web.kakao.exception.KakaoSearchBadRequestException;
import com.searchservice.adapter.web.kakao.exception.KakaoSearchInternalException;
import com.searchservice.client.config.DefaultErrorDecoder;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public class KakaoSearchApiClientErrorDecoder extends DefaultErrorDecoder {

    @Override
    public Exception decodeError(String methodKey, Response response) {
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new KakaoSearchBadRequestException();
        }
        return new KakaoSearchInternalException();
    }
}
