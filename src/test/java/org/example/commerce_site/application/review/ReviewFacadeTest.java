package org.example.commerce_site.application.review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.commerce_site.application.order.OrderService;
import org.example.commerce_site.application.review.dto.ReviewRequestDto;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewFacadeTest {
	@InjectMocks
	private ReviewFacade reviewFacade;

	@Mock
	private UserService userService;

	@Mock
	private ReviewService reviewService;

	@Mock
	private OrderService orderService;

	private User user = User.builder().id(1L).authId("testUserAuth").build();
	private ReviewRequestDto.Create dto = ReviewRequestDto.Create.builder()
		.authId("testUserAuth")
		.comment("testComment")
		.productId(1L)
		.rating(5)
		.build();

	@Test
	void create_ShouldCreateReview_WhenUserPurchasedProduct() {
		when(userService.getUser(dto.getAuthId())).thenReturn(user);
		when(orderService.isProductPurchasedByUser(user, dto.getProductId())).thenReturn(true);

		reviewFacade.create(dto);

		verify(userService).getUser(dto.getAuthId());
		verify(orderService).isProductPurchasedByUser(user, dto.getProductId());
		verify(reviewService).create(any());
	}

	@Test
	void create_ShouldThrowException_WhenUserDidNotPurchaseProduct() {
		when(userService.getUser(dto.getAuthId())).thenReturn(user);
		when(orderService.isProductPurchasedByUser(user, dto.getProductId())).thenReturn(false);

		CustomException exception = assertThrows(CustomException.class, () -> reviewFacade.create(dto));

		assertEquals(ErrorCode.NO_PURCHASE_HISTORY, exception.getErrorCode());
		verify(userService).getUser(dto.getAuthId());
		verify(orderService).isProductPurchasedByUser(user, dto.getProductId());
		verify(reviewService, never()).create(any());
	}
}