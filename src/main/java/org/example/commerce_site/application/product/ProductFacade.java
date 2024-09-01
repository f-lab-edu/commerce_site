package org.example.commerce_site.application.product;

import java.util.Objects;

import org.example.commerce_site.application.category.CategoryService;
import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.domain.Product;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFacade {
	private final ProductService productService;
	private final CategoryService categoryService;

	public ProductResponseDto.Create createProduct(ProductRequestDto.Create dto) {
		Category category = categoryService.getCategoryById(dto.getCategoryId());
		return ProductResponseDto.Create.of(productService.create(dto, category));
	}

	public void updateProduct(Long productId, ProductRequestDto.Put dto) {
		// id가 존재하는 상품인지 확인
		Product product = productService.getProduct(productId);

		// 파트너 본인이 올린 상품인지 확인
		if (!Objects.equals(product.getPartnerId(), dto.getPartnerId())) {
			throw new CustomException(ErrorCode.PRODUCT_ACCESS_DENIED);
		}

		//카테고리 변경시 카테고리 아이디 존재 확인
		Category category = null;
		if (dto.getCategoryId() != null) {
			category = categoryService.getCategoryById(dto.getCategoryId());
		}

		productService.update(product, dto, category);
	}

	public void deleteProduct(Long productId) {
		productService.delete(productService.getProduct(productId));
	}
}
