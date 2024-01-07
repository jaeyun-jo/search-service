package com.searchservice.adapter.web.naver.config;

import com.searchservice.adapter.web.naver.exception.NaverSearchBadRequestException;
import com.searchservice.adapter.web.naver.exception.NaverSearchInternalException;
import com.searchservice.client.config.DefaultErrorDecoder;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public class NaverSearchApiClientErrorDecoder extends DefaultErrorDecoder {

    @Override
    public Exception decodeError(String methodKey, Response response) {
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new NaverSearchBadRequestException();
        }
        return new NaverSearchInternalException();
    }
}
