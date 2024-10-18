package org.example.commerce_site.application.cart;

import java.util.HashMap;
import java.util.List;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Cart;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.domain.User;
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
	public void create(CartRequestDto.Create dto, User user, Product product) {
		if (dto.getQuantity() == 0) {
			throw new CustomException(ErrorCode.QUANTITY_IS_ZERO);
		}
		cartRepository.findByUserIdAndProductId(user.getId(), product.getId()).ifPresentOrElse(
			cart -> cart.addQuantity(dto.getQuantity()),
			() -> cartRepository.save(CartRequestDto.Create.toEntity(dto, user.getId()))
		);
	}

	@Transactional
	public void delete(CartRequestDto.Delete dto, User user) {
		dto.getProductIds().forEach(productId -> {
			cartRepository.deleteByUserIdAndProductId(user.getId(), productId);
		});
	}

	@Transactional
	public void update(CartRequestDto.Update dto, User user) {
		HashMap<Long, Long> productIdAndQuantity = dto.getProductsMap();

		if (productIdAndQuantity.values().stream().anyMatch(quantity -> quantity == 0)) {
			throw new CustomException(ErrorCode.QUANTITY_IS_ZERO);
		}

		List<Cart> cartList = cartRepository.findByUserIdAndProductIdIn(user.getId(), productIdAndQuantity.keySet());
		cartList.forEach(cart -> {
			Long newQuantity = productIdAndQuantity.get(cart.getProductId());
			cart.updateQuantity(newQuantity);
		});

		cartRepository.saveAll(cartList);
	}

	public Page<CartResponseDto.Get> getList(Long userId, PageRequest of) {
		return customCartRepository.getCartListByUserId(userId, of);
	}
}
