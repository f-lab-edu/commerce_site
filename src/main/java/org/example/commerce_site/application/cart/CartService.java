package org.example.commerce_site.application.cart;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.infrastructure.cart.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;

	@Transactional
	public void create(CartRequestDto.Create dto) {
		if (dto.getQuantity() == 0) {
			throw new CustomException(ErrorCode.QUANTITY_IS_ZERO);
		}
		cartRepository.findByUserIdAndProductId(dto.getUserId(), dto.getProductId()).ifPresentOrElse(
			cart -> cart.updateQuantity(dto.getQuantity()),
			() -> cartRepository.save(CartRequestDto.Create.toEntity(dto))
		);
	}

	@Transactional
	public void delete(Long userId, Long productId) {
		cartRepository.deleteByUserIdAndProductId(userId, productId);
	}
}
