package org.example.commerce_site.application.product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Category;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.infrastructure.product.CustomProductRepository;
import org.example.commerce_site.infrastructure.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final CustomProductRepository customProductRepository;

	@Transactional
	public Product create(ProductRequestDto.Create productRequest, Category category, Long partnerId) {
		return productRepository.save(ProductRequestDto.Create.toEntity(productRequest, category, partnerId));
	}

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	public Page<ProductResponseDto.Get> getProductList(PageRequest of, String keyword, Long categoryId,
		Long partnerId) {
		return customProductRepository.getProducts(of, keyword, categoryId, partnerId);
	}

	@Transactional
	public void decreaseStockOnPurchase(List<OrderRequestDto.CreateDetail> details) {
		List<Long> productIds = details.stream()
			.map(OrderRequestDto.CreateDetail::getProductId)
			.toList();

		List<Product> products = productRepository.findByIdInWithLock(productIds);

		Map<Long, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getId, product -> product));

		for (OrderRequestDto.CreateDetail detail : details) {
			Product product = productMap.get(detail.getProductId());

			if (product == null) {
				throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
			}

			long newStockQuantity = product.getStockQuantity() - detail.getQuantity();

			if (newStockQuantity < 0) {
				throw new CustomException(ErrorCode.PRODUCT_OUT_OF_STOCK);
			}

			product.updateQuantity(newStockQuantity);
		}

		productRepository.saveAll(products);
	}

	@Transactional
	public void restoreStockOnCancel(List<OrderDetailResponseDto.Get> details) {
		List<Long> productIds = details.stream()
			.map(OrderDetailResponseDto.Get::getProductId)
			.toList();

		List<Product> products = productRepository.findByIdInWithLock(productIds);

		Map<Long, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getId, product -> product));

		for (OrderDetailResponseDto.Get detail : details) {
			Product product = productMap.get(detail.getProductId());

			if (product == null) {
				throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
			}

			long newStockQuantity = product.getStockQuantity() + detail.getQuantity();
			product.updateQuantity(newStockQuantity);
		}
		productRepository.saveAll(products);
	}
}
