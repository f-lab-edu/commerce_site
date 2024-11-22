package org.example.commerce_site.representation.review.dto;

import org.example.commerce_site.application.review.dto.ReviewRequestDto;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

public class ReviewRequest {
	@Getter
	@ToString
	public static class Create {
		@NotNull
		private Long productId;

		@NotNull
		@Range(min = 1, max = 5)
		private Integer rating;

		@NotBlank
		private String comment;

		public static ReviewRequestDto.Create toDto(ReviewRequest.Create create, String authId) {
			return ReviewRequestDto.Create.builder()
				.authId(authId)
				.productId(create.productId)
				.rating(create.rating)
				.comment(create.comment)
				.build();
		}
	}
}
