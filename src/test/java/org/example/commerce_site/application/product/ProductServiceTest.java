package org.example.commerce_site.application.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.infrastructure.product.CustomProductRepository;
import org.example.commerce_site.infrastructure.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CustomProductRepository customProductRepository;

	private Long productId;
	private Long partnerId;
	private Product product;

	@BeforeEach
	void setUp() {
		productId = 1L;
		partnerId = 1L;
		product = Product.builder().id(productId).partnerId(partnerId).category(new Category()).build();
	}

	@Test
	void create_ShouldCreateProduct() {
		ProductRequestDto.Create dto = ProductRequestDto.Create.builder()
			.name("Test Product")
			.partnerAuthId("testAuth")
			.categoryId(1L)
			.build();

		Category category = new Category();
		Product createdProduct = ProductRequestDto.Create.toEntity(dto, category, 1L);

		when(productRepository.save(any(Product.class))).thenReturn(createdProduct);

		Product result = productService.create(dto, category, 1L);

		assertNotNull(result);
		assertEquals(dto.getName(), result.getName());
		verify(productRepository).save(any(Product.class));
	}

	@Test
	void get_ShouldReturnProduct() {
		when(productRepository.findById(productId)).thenReturn(Optional.of(product));

		Product retrievedProduct = productService.getProduct(productId);

		assertNotNull(retrievedProduct);
		assertEquals(productId, retrievedProduct.getId());
		verify(productRepository).findById(productId);
	}

	@Test
	void get_ShouldNotReturnProduct() {
		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		CustomException exception = assertThrows(CustomException.class, () -> {
			productService.getProduct(productId);
		});

		assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
	}

	@Test
	void update_ShouldUpdateProduct() {
		ProductRequestDto.Put dto = ProductRequestDto.Put.builder()
			.categoryId(1L)
			.partnerAuthId("testAuth")
			.build();

		Category category = new Category();

		lenient().when(productRepository.findById(productId)).thenReturn(Optional.of(product));
		when(productRepository.save(any(Product.class))).thenReturn(product);

		Product updatedProduct = productService.update(product, dto, category);

		assertNotNull(updatedProduct);
		assertEquals(productId, updatedProduct.getId());
		verify(productRepository).save(product);
	}

	@Test
	void delete_ShouldDeleteProduct() {
		Product product = Product.builder().id(productId).partnerId(partnerId).build();
		lenient().when(productRepository.findById(productId)).thenReturn(Optional.of(product));

		productService.delete(product);

		verify(productRepository).delete(product);
	}

	@Test
	void getList_ShouldReturnProducts() {
		String keyword = "test";
		Long categoryId = 1L;

		PageRequest pageRequest = PageRequest.of(0, 10);
		List<ProductResponseDto.Get> productList = List.of(ProductResponseDto.Get.of(product, new Partner()));
		Page<ProductResponseDto.Get> expectedPage = new PageImpl<>(productList, pageRequest, productList.size());

		when(customProductRepository.getProducts(pageRequest, keyword, categoryId, partnerId)).thenReturn(expectedPage);

		Page<ProductResponseDto.Get> resultPage = productService.getProductList(pageRequest, keyword, categoryId,
			partnerId);

		assertEquals(expectedPage.getTotalElements(), resultPage.getTotalElements());
		verify(customProductRepository).getProducts(pageRequest, keyword, categoryId, partnerId);
	}
}