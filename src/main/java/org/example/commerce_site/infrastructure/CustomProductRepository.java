package org.example.commerce_site.infrastructure;

import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {
	Page<ProductResponseDto.Get> getProducts(Pageable pageable, String keyword, Long categoryId, Long PartnerId);
}
