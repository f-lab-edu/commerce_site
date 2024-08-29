package org.example.commerce_site.application.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.category.CategoryService;
import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.example.commerce_site.domain.Category;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductResponseDto.Create createProduct(ProductRequestDto.Create request) {
        Category category = categoryService.getCategoryById(request.getCategoryId());
        return ProductResponseDto.Create.of(productService.create(request, category));
    }
}
