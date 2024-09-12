package org.example.commerce_site.application.cart;

import java.util.HashMap;

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
			cart -> cart.addQuantity(dto.getQuantity()),
			() -> cartRepository.save(CartRequestDto.Create.toEntity(dto))
		);
	}

	@Transactional
	public void delete(CartRequestDto.Delete dto) {
		dto.getProductIds().forEach(productId -> {
			cartRepository.deleteByUserIdAndProductId(dto.getUserId(), productId);
		});
	}

	@Transactional
	public void update(CartRequestDto.Update dto) {
		HashMap<Long, Long> productIdAndQuantity = dto.getProductsMap();
		productIdAndQuantity.forEach(
			(key, value) -> cartRepository.findByUserIdAndProductId(dto.getUserId(), key).ifPresent(
				cart -> {
					if (value == 0) {
						throw new CustomException(ErrorCode.QUANTITY_IS_ZERO);
					}
					cart.updateQuantity(value);
					cartRepository.save(cart);
				}
			));
	}
}
