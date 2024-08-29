package org.example.commerce_site.application.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.infrastructure.CategoryRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                //TODO exception 재정의
                () -> new RuntimeException("Category with id " + id + " not found")
        );
    }
}
