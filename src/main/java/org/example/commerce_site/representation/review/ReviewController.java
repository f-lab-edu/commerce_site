package org.example.commerce_site.representation.review;

import org.example.commerce_site.application.review.ReviewFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.review.dto.ReviewRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/reviews")
public class ReviewController {
	private final ReviewFacade reviewFacade;

	@PostMapping()
	public ApiSuccessResponse createReview(
		@RequestAttribute("user_id") String userAuthId,
		@Valid @RequestBody ReviewRequest.Create request
	) {
		reviewFacade.create(ReviewRequest.Create.toDto(request, userAuthId));
		return new ApiSuccessResponse();
	}
}
