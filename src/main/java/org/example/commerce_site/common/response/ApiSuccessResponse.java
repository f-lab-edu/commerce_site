package org.example.commerce_site.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiSuccessResponse<T> {
	private T data;

	public static <T> ApiSuccessResponse<T> success(T data) {
		return (ApiSuccessResponse<T>)ApiSuccessResponse.builder()
			.data(data)
			.build();
	}

	//for void api
	public static ApiSuccessResponse success() {
		return ApiSuccessResponse.builder()
			.build();
	}
}
