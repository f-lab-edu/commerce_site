package org.example.commerce_site.application.review.dto;

import org.example.commerce_site.domain.Review;

import lombok.Builder;
import lombok.Getter;

public class ReviewRequestDto {
	@Getter
	@Builder
	public static class Create {
		private String authId;
		private Long productId;
		private Integer rating;
		private String comment;

		public static Review toEntity(ReviewRequestDto.Create create, Long userId) {
			return Review.builder()
				.userId(userId)
				.productId(create.productId)
				.comment(create.comment)
				.rating(create.rating)
				.build();
		}
	}
}
