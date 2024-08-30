package org.example.commerce_site.representation.product;

import org.example.commerce_site.application.product.ProductFacade;
import org.example.commerce_site.common.response.CommonResponse;
import org.example.commerce_site.representation.product.request.ProductRequest;
import org.example.commerce_site.representation.product.response.ProductResponse;
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
	public CommonResponse.CommonData<ProductResponse.Create> createProduct(
		@Valid @RequestBody ProductRequest.Create request) {
		//TODO Partner 회원 외에는 접근할 수 없는 API 임
		return CommonResponse.success(
			ProductResponse.Create.of(productFacade.createProduct(ProductRequest.Create.toDTO(request))));
	}
}
