package org.example.commerce_site.infrastructure.cart;

import java.util.List;

import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.example.commerce_site.common.util.PageConverter;
import org.example.commerce_site.domain.QCart;
import org.example.commerce_site.domain.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomCartRepositoryImpl implements CustomCartRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<CartResponseDto.Get> getCartListByUserId(Long userId, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(QCart.cart.userId.eq(userId));

		List<CartResponseDto.Get> carts = queryFactory.select(
				Projections.constructor(CartResponseDto.Get.class,
					QCart.cart.id,
					QCart.cart.productId,
					QProduct.product.name,
					QProduct.product.category.id,
					QProduct.product.category.name,
					QProduct.product.price,
					QCart.cart.quantity,
					QProduct.product.partnerId
				))
			.from(QCart.cart)
			.leftJoin(QProduct.product)
			.on(QCart.cart.productId.eq(QProduct.product.id))
			.where(builder)
			.fetch();

		return PageConverter.getPage(carts, pageable);
	}
}
