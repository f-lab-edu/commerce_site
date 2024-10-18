package org.example.commerce_site.application.cart;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartFacade {
	private final CartService cartService;
	private final UserService userService;
	private final ProductService productService;

	public void create(CartRequestDto.Create dto) {
		User user = userService.getUser(dto.getUserAuthId());
		Product product = productService.getProduct(dto.getProductId());
		cartService.create(dto, user, product);
	}

	public void delete(CartRequestDto.Delete dto) {
		User user = userService.getUser(dto.getUserAuthId());
		cartService.delete(dto, user);
	}

	public void update(CartRequestDto.Update dto) {
		User user = userService.getUser(dto.getUserAuthId());
		cartService.update(dto, user);
	}

	public Page<CartResponseDto.Get> getList(String userAuthId, int page, int size) {
		User user = userService.getUser(userAuthId);
		return cartService.getList(user.getId(), PageRequest.of(page, size));
	}
}
