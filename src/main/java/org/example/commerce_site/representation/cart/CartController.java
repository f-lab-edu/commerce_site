package org.example.commerce_site.representation.cart;

import org.example.commerce_site.application.cart.CartFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.cart.request.CartRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
	private final CartFacade cartFacade;

	@PostMapping("/{user_id}")
	public ApiSuccessResponse createCart(
		@RequestParam(name = "user_id") Long userId,
		@RequestBody @Valid CartRequest.Create request) {
		cartFacade.create(CartRequest.Create.toDto(request, userId));
		return ApiSuccessResponse.success();
	}

	@DeleteMapping("/{user_id}/{product_id}")
	public ApiSuccessResponse createCart(
		@RequestParam(name = "user_id") Long userId,
		@RequestParam(name = "product_id") Long productId) {
		cartFacade.delete(userId, productId);
		return ApiSuccessResponse.success();
	}

	@PatchMapping("/{user_id}")
	public ApiSuccessResponse updateCart(
		@RequestParam(name = "user_id") Long userId,
		@RequestBody @Valid CartRequest.Update request
	) {
		cartFacade.update(CartRequest.Update.toDto(request, userId));
		return ApiSuccessResponse.success();
	}
}
