package org.example.commerce_site.application.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

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

	private Category category = Category.builder().id(1L).build();
	private Partner partner = Partner.builder().id(1L).authId("testPartnerAuth").build();;

	@Test
	void create_ShouldCreateProduct() {
		ProductRequestDto.Create createDto = ProductRequestDto.Create.builder()
			.partnerAuthId("testPartnerAuth")
			.categoryId(1L)
			.build();

		when(partnerService.getPartner(createDto.getPartnerAuthId())).thenReturn(partner);
		when(categoryService.getCategoryById(createDto.getCategoryId())).thenReturn(category);

		productFacade.createProduct(createDto);

		verify(partnerService).getPartner(createDto.getPartnerAuthId());
		verify(categoryService).getCategoryById(createDto.getCategoryId());
		verify(productService).create(createDto, category, partner.getId());
	}

	@Test
	void update_ShouldUpdateProduct() {
		Long productId = 1L;
		ProductRequestDto.Put updateDto = ProductRequestDto.Put.builder()
			.partnerAuthId("testPartnerAuth")
			.build();

		Product product = Product.builder().id(productId).partnerId(partner.getId()).build();
		when(partnerService.getPartner(updateDto.getPartnerAuthId())).thenReturn(partner);
		when(productService.getProduct(productId)).thenReturn(product);

		productFacade.updateProduct(productId, updateDto);

		verify(partnerService).getPartner(updateDto.getPartnerAuthId());
		verify(productService).getProduct(productId);
		verify(productService).update(product, updateDto, null);

	}

	@Test
	void update_ShouldNotUpdate_AccessDenied() {
		Long productId = 1L;
		ProductRequestDto.Put updateDto = ProductRequestDto.Put.builder()
			.partnerAuthId("testPartnerAuth")
			.build();

		Partner differentPartner = Partner.builder().id(2L).build();
		Product product = Product.builder().id(productId).partnerId(differentPartner.getId()).build();

		when(partnerService.getPartner(updateDto.getPartnerAuthId())).thenReturn(partner);
		when(productService.getProduct(productId)).thenReturn(product);

		CustomException thrown = assertThrows(CustomException.class, () -> {
			productFacade.updateProduct(productId, updateDto);
		});

		assertEquals(ErrorCode.PRODUCT_ACCESS_DENIED, thrown.getErrorCode());

		verify(partnerService).getPartner(updateDto.getPartnerAuthId());
		verify(productService).getProduct(productId);
		verify(productService, never()).update(any(), any(), any());
	}

	@Test
	void delete_ShouldDeleteProduct() {
		Long productId = 1L;
		String partnerAuthId = "testPartnerAuth";

		Product product = Product.builder().id(productId).partnerId(partner.getId()).build();
		when(partnerService.getPartner(partnerAuthId)).thenReturn(partner);
		when(productService.getProduct(productId)).thenReturn(product);

		productFacade.deleteProduct(productId, partnerAuthId);

		verify(partnerService).getPartner(partnerAuthId);
		verify(productService).getProduct(productId);
		verify(productService).delete(product);
	}

	@Test
	void get_ShouldReturnProduct() {
		Partner partner = new Partner();
		Product product = Product.builder().id(1L).partnerId(1L).category(new Category()).build();

		when(productService.getProduct(1L)).thenReturn(product);
		when(partnerService.getPartner(product.getPartnerId())).thenReturn(partner);

		ProductResponseDto.Get result = productFacade.getProduct(1L);

		assertNotNull(result);
		verify(partnerService).getPartner(product.getPartnerId());
	}

	@Test
	void getList_ShouldReturnProducts() {
		int page = 0;
		int size = 10;
		String keyword = "test";
		Long categoryId = 1L;
		Long partnerId = 2L;

		ProductResponseDto.Get productDto = ProductResponseDto.Get.builder()
			.categoryId(categoryId)
			.partnerId(partnerId)
			.build();
		Page<ProductResponseDto.Get> expectedPage = new PageImpl<>(List.of(productDto));

		when(productService.getProductList(PageRequest.of(page, size), keyword, categoryId, partnerId))
			.thenReturn(expectedPage);

		Page<ProductResponseDto.Get> result = productFacade.getProductList(page, size, keyword, categoryId, partnerId);

		assertEquals(expectedPage.getContent().size(), result.getContent().size());
		verify(productService).getProductList(PageRequest.of(page, size), keyword, categoryId, partnerId);
	}
}
