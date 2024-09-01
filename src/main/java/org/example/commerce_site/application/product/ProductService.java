package org.example.commerce_site.application.product;

import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.infrastructure.ProductRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	@Transactional
	public Product create(ProductRequestDto.Create productRequest, Category category) {
		return productRepository.save(ProductRequestDto.Create.toEntity(productRequest, category));
	}

	public Product getProduct(Long productId) {
		return productRepository.findById(productId).orElseThrow(
			() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND)
		);
	}

	@Transactional
	public Product update(Product product, ProductRequestDto.Put dto, Category category) {
		product.update(dto, category);
		return productRepository.save(product);
	}

	@Transactional
	public void delete(Product product) {
		productRepository.delete(product);
	}
}
