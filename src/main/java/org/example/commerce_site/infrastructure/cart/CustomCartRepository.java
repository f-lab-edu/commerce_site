package org.example.commerce_site.infrastructure.cart;

import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCartRepository {
	Page<CartResponseDto.Get> getCartListByUserId(Long userId, Pageable pageable);
}
