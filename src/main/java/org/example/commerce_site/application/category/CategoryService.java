package org.example.commerce_site.application.category;

import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.infrastructure.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
		);
	}
}
