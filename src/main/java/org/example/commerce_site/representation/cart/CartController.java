package org.example.commerce_site.representation.cart;

import org.example.commerce_site.application.cart.CartFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.cart.request.CartRequest;
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
}
