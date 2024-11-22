package org.example.commerce_site.application.review;

import org.example.commerce_site.application.order.OrderService;
import org.example.commerce_site.application.review.dto.ReviewRequestDto;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewFacade {
	private final UserService userService;
	private final ReviewService reviewService;
	private final OrderService orderService;

	public void create(ReviewRequestDto.Create dto) {
		User user = userService.getUser(dto.getAuthId());
		if (!orderService.isProductPurchasedByUser(user, dto.getProductId())) {
			throw new CustomException(ErrorCode.NO_PURCHASE_HISTORY);
		}
		reviewService.create(ReviewRequestDto.Create.toEntity(dto, user.getId()));
	}
}
