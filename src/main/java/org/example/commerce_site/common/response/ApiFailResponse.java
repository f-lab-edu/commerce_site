package org.example.commerce_site.common.response;

import org.example.commerce_site.common.exception.ErrorCode;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiFailResponse {
	private int code;
	private String message;

	public static <T> ResponseEntity<T> fail(ErrorCode errorCode) {
		return (ResponseEntity<T>)ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(ApiFailResponse.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build()
			);
	}
}
