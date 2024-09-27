package org.example.commerce_site.application.cart;

import java.util.HashMap;
import java.util.Optional;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Cart;
import org.example.commerce_site.infrastructure.cart.CartRepository;
import org.example.commerce_site.infrastructure.cart.CustomCartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final CustomCartRepository customCartRepository;

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
			(key, value) -> {
				Optional<Cart> optionalCart = cartRepository.findByUserIdAndProductId(dto.getUserId(), key);
				if (optionalCart.isPresent()) {
					Cart cart = optionalCart.get();
					if (value == 0) {
						throw new CustomException(ErrorCode.QUANTITY_IS_ZERO);
					}
					cart.updateQuantity(value);
					cartRepository.save(cart);
				}
			}
		);
	}

	public Page<CartResponseDto.Get> getList(Long userId, PageRequest of) {
		return customCartRepository.getCartListByUserId(userId, of);
	}
}
