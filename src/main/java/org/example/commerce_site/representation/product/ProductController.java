package org.example.commerce_site.representation.product;

import org.example.commerce_site.application.product.ProductFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.product.request.ProductRequest;
import org.example.commerce_site.representation.product.response.ProductResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
	private final ProductFacade productFacade;

	@PostMapping()
	public ApiSuccessResponse<ProductResponse.Create> createProduct(
		@Valid @RequestBody ProductRequest.Create request) {
		//TODO Partner 회원 외에는 접근할 수 없는 API 임
		return ApiSuccessResponse.success(
			ProductResponse.Create.of(productFacade.createProduct(ProductRequest.Create.toDTO(request))));
	}

	@PatchMapping("/{product_id}")
	public ApiSuccessResponse updateProduct(
		@PathVariable(name = "product_id") Long productId,
		@Valid @RequestBody ProductRequest.Update request) {
		//TODO Partner 회원 외에는 접근할 수 없는 API 임
		//TODO 파트너 자신이 등록한 상품만 수정 가능해야 함
		productFacade.updateProduct(productId, ProductRequest.Update.toDTO(request));
		return ApiSuccessResponse.success();
	}

	@DeleteMapping("/{product_id}")
	public ApiSuccessResponse deleteProduct(
		@PathVariable(name = "product_id") Long productId) {
		//TODO Partner 회원 외에는 접근할 수 없는 API 임
		//TODO 파트너 자신이 등록한 상품만 수정 가능해야 함
		productFacade.deleteProduct(productId);
		return ApiSuccessResponse.success();
	}
}
