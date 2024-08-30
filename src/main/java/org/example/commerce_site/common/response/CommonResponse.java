package org.example.commerce_site.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commerce_site.common.exception.ErrorCode;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private static final String MESSAGE_SUCCESS = "SUCCESS";
    private int code;
    private String message;

    public static <T> CommonResponse.CommonData<T> success(T data) {
        return (CommonResponse.CommonData<T>) CommonData.builder()
                .message(MESSAGE_SUCCESS)
                .data(data)
                .build();
    }

    //for void api
    public static CommonResponse success() {
        return CommonResponse.builder()
            .message(MESSAGE_SUCCESS)
            .build();
    }

    public static <T> ResponseEntity<T> fail(ErrorCode errorCode) {
        return (ResponseEntity<T>) ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CommonResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommonData<T> {
        private int code;
        private String message;
        private T data;
    }
}
