package org.example.commerce_site.application.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.commerce_site.application.category.CategoryService;
import org.example.commerce_site.application.partner.PartnerService;
import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductFacadeTest {

	@InjectMocks
	private ProductFacade productFacade;

	@Mock
	private ProductService productService;

	@Mock
	private CategoryService categoryService;

	@Mock
	private PartnerService partnerService;

	private Long productId;
	private Product product;

	@BeforeEach
	void setUp() {
		productId = 1L;
		product = Product.builder().id(productId).partnerId(1L).category(new Category()).build();
	}

	@Test
	void testCreateProduct() {
		ProductRequestDto.Create dto = ProductRequestDto.Create.builder().categoryId(1L).build();

		Category category = new Category();
		when(categoryService.getCategoryById(dto.getCategoryId())).thenReturn(category);

		productFacade.createProduct(dto);

		verify(productService).create(dto, category);
	}

	@Test
	void testUpdateProduct() {
		ProductRequestDto.Put dto = ProductRequestDto.Put.builder().categoryId(1L).partnerId(1L).build();

		when(productService.getProduct(productId)).thenReturn(product);
		when(categoryService.getCategoryById(dto.getCategoryId())).thenReturn(new Category());

		productFacade.updateProduct(productId, dto);

		verify(productService).update(eq(product), eq(dto), any());
	}

	@Test
	void testUpdateProductAccessDenied() {
		ProductRequestDto.Put dto = ProductRequestDto.Put.builder().categoryId(1L).partnerId(2L).build();

		when(productService.getProduct(productId)).thenReturn(product);

		CustomException thrown = assertThrows(CustomException.class, () -> {
			productFacade.updateProduct(productId, dto);
		});

		assertEquals(ErrorCode.PRODUCT_ACCESS_DENIED, thrown.getErrorCode());
	}

	@Test
	void testDeleteProduct() {
		when(productService.getProduct(productId)).thenReturn(product);

		productFacade.deleteProduct(productId);

		verify(productService).delete(product);
	}

	@Test
	void testGetProduct() {
		Partner partner = new Partner();

		when(productService.getProduct(productId)).thenReturn(product);
		when(partnerService.getPartner(product.getPartnerId())).thenReturn(partner);

		ProductResponseDto.Get result = productFacade.getProduct(productId);

		assertNotNull(result);
		verify(partnerService).getPartner(product.getPartnerId());
	}

	@Test
	void testGetProductList() {
		int page = 0;
		int size = 10;
		String keyword = "test";
		Long categoryId = 1L;
		Long partnerId = 2L;

		ProductResponseDto.Get productDto = ProductResponseDto.Get.builder().categoryId(categoryId).partnerId(partnerId).build();
		Page<ProductResponseDto.Get> expectedPage = new PageImpl<>(List.of(productDto));

		when(productService.getProductList(PageRequest.of(page, size), keyword, categoryId, partnerId))
			.thenReturn(expectedPage);

		Page<ProductResponseDto.Get> result = productFacade.getProductList(page, size, keyword, categoryId, partnerId);

		assertEquals(expectedPage.getContent().size(), result.getContent().size());
		verify(productService).getProductList(PageRequest.of(page, size), keyword, categoryId, partnerId);
	}
}
