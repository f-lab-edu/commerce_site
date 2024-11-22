package org.example.commerce_site.application.review;

import static org.mockito.Mockito.*;

import org.example.commerce_site.domain.Review;
import org.example.commerce_site.infrastructure.review.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
	@InjectMocks
	private ReviewService reviewService;

	@Mock
	private ReviewRepository reviewRepository;

	@Test
	void create_ShouldCreateReview() {
		Review review = Review.builder()
			.id(1L)
			.rating(5)
			.comment("testComment")
			.productId(1L)
			.userId(1L)
			.build();

		reviewService.create(review);

		verify(reviewRepository).save(review);
	}

}