package org.example.commerce_site.application.review;

import org.example.commerce_site.domain.Review;
import org.example.commerce_site.infrastructure.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;

	@Transactional
	public void create(Review review) {
		reviewRepository.save(review);
	}
}
