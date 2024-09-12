package org.example.commerce_site.application.cart;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.user.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartFacade {
	private final CartService cartService;
	private final UserService userService;
	private final ProductService productService;

	public void create(CartRequestDto.Create dto) {
		userService.getUser(dto.getUserId());
		productService.getProduct(dto.getProductId());
		cartService.create(dto);
	}

	public void delete(CartRequestDto.Delete dto) {
		userService.getUser(dto.getUserId());
		cartService.delete(dto);
	}

	public void update(CartRequestDto.Update dto) {
		userService.getUser(dto.getUserId());
		cartService.update(dto);
	}
}
