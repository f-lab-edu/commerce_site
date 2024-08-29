package org.example.commerce_site.application.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.infrastructure.ProductRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(ProductRequestDto.Create productRequest, Category category) {
        return productRepository.save(ProductRequestDto.Create.toEntity(productRequest, category));
    }
}
