package com.searchservice.apiserver.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse<T> {
    private Status status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;
    private T data;

    private CommonResponse(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(Status.SUCCESS, Status.SUCCESS.getMessage(), data);
    }
    public static <T> CommonResponse<T> error(String message) {
        return new CommonResponse<>(Status.ERROR, message, null);
    }

    @Getter
    public enum Status {
        SUCCESS("성공"),
        ERROR("에러"),
        FAIL("실패");

        private String message;

        Status(String message) {
            this.message = message;
        }
    }
}
