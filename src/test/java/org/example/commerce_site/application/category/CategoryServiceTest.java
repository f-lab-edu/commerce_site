package org.example.commerce_site.application.category;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.infrastructure.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@InjectMocks
	private CategoryService categoryService;

	@Mock
	private CategoryRepository categoryRepository;


	@Test
	void getCategoryById_ShouldReturnCategory_WhenCategoryExists() {
		Long categoryId = 1L;
		Category category = Category.builder().id(categoryId).name("Electronics").build();

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

		Category result = categoryService.getCategoryById(categoryId);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(categoryId);
		assertThat(result.getName()).isEqualTo("Electronics");

		verify(categoryRepository, times(1)).findById(categoryId);
	}

	@Test
	void getCategoryById_ShouldThrowCustomException_WhenCategoryDoesNotExist() {
		Long categoryId = 1L;

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> categoryService.getCategoryById(categoryId))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.CATEGORY_NOT_FOUND);

		verify(categoryRepository, times(1)).findById(categoryId);
	}
}