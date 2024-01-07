package com.searchservice.client.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public abstract class DefaultErrorDecoder implements ErrorDecoder {

    public abstract Exception decodeError(String methodKey, Response response);

    @Override
    public Exception decode(String methodKey, Response response) {
        logging(methodKey, response);
        return decodeError(methodKey, response);
    }

    private void logging(String methodKey, Response response) {
        if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
            log.error("{} client 500 error - Request: {}, Response: {}", methodKey, response.request(), response);
        } else {
            log.warn("{} client error - Request: {}, Response: {}", methodKey, response.request(), response);
        }
    }
}
