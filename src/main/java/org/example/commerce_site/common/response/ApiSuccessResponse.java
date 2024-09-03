package org.example.commerce_site.common.response;

import org.springframework.data.domain.Page;

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

	//for list api
	public static <T> ApiSuccessResponse.PageList<T> success(Page<T> list) {
		return new PageList<>(list);
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PageList<T> {
		private T list;
		private int pageNumber;
		private int pageSize;
		private Long totalCount;
		private int totalPage;

		PageList(Page<T> list) {
			if (!list.getContent().isEmpty()){
				this.list = (T)list.getContent();
			}
			this.pageNumber = list.getNumber();
			this.pageSize = list.getSize();
			this.totalCount = list.getTotalElements();
			this.totalPage = list.getTotalPages();
		}
	}
}
