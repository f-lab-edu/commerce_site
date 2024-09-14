package org.example.commerce_site.representation.product;

import org.example.commerce_site.application.product.ProductFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.product.request.ProductRequest;
import org.example.commerce_site.representation.product.response.ProductResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	private final ProductFacade productFacade;

	@PostMapping()
	public ApiSuccessResponse createProduct(
		@Valid @RequestBody ProductRequest.Create request) {
		//TODO Partner 회원 외에는 접근할 수 없는 API 임
		productFacade.createProduct(ProductRequest.Create.toDTO(request));
		return ApiSuccessResponse.success();
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

	@GetMapping("/{product_id}")
	public ApiSuccessResponse<ProductResponse.Get> getProduct(
		@PathVariable(name = "product_id") Long productId) {
		return ApiSuccessResponse.success(ProductResponse.Get.of(productFacade.getProduct(productId)));
	}

	@GetMapping("/list")
	public ApiSuccessResponse.PageList<ProductResponse.Get> getProductList(
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size,
		@RequestParam(value = "keyword", required = false) String keyword,
		@RequestParam(value = "category_id", required = false) Long categoryId,
		@RequestParam(value = "partner_id", required = false) Long partnerId
	) {
		return ApiSuccessResponse.success(
			ProductResponse.Get.of(productFacade.getProductList(page - 1, size, keyword, categoryId, partnerId)));
	}
}
