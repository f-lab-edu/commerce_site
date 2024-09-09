package org.example.commerce_site.infrastructure;

import java.util.List;

import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.example.commerce_site.common.util.PageConverter;
import org.example.commerce_site.domain.QCategory;
import org.example.commerce_site.domain.QPartner;
import org.example.commerce_site.domain.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
	private final JPAQueryFactory queryFactory;
	@Override
	public Page<ProductResponseDto.Get> getProducts(Pageable pageable, String keyword, Long categoryId, Long PartnerId) {

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(QProduct.product.isEnable.eq(true));
		builder.and(QProduct.product.isDeleted.eq(false));

		if (StringUtils.hasText(keyword)) {
			builder.and(QProduct.product.name.contains(keyword));
		}
		if (categoryId != null) {
			builder.and(QProduct.product.category.id.eq(categoryId));
		}
		if (PartnerId != null) {
			builder.and(QProduct.product.partnerId.eq(PartnerId));
		}

		List<ProductResponseDto.Get> products = queryFactory.select(Projections.constructor(ProductResponseDto.Get.class,
				QProduct.product.id,
				QProduct.product.partnerId,
				QPartner.partner.name,
				QCategory.category.id,
				QCategory.category.name,
				QProduct.product.name,
				QProduct.product.description,
				QProduct.product.price,
				QProduct.product.stockQuantity,
				QProduct.product.createdAt
			))
			.from(QProduct.product)
			.leftJoin(QCategory.category)
			.on(QProduct.product.category.id.eq(QCategory.category.id))
			.leftJoin(QPartner.partner)
			.on(QProduct.product.partnerId.eq(QPartner.partner.id))
			.where(builder)

			//sort, order by
			.fetch();

		return PageConverter.getPage(products, pageable);
	}
}
