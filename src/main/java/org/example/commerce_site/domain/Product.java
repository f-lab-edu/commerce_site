package org.example.commerce_site.domain;

import org.example.commerce_site.application.product.dto.ProductRequestDto;
import org.example.commerce_site.common.domain.BaseTimeEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE products SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted IS FALSE")
@Table(name = "products")
public class Product extends BaseTimeEntity {
	private Long partnerId;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private String name;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private String description;

	private Long price;

	private Long stockQuantity;

	private Boolean isEnable;

	private Boolean isDeleted;

	public void update(ProductRequestDto.Put dto, Category category) {
		this.name = dto.getName() != null ? dto.getName() : this.name;
		this.description = dto.getDescription() != null ? dto.getDescription() : this.description;
		this.price = dto.getPrice() != null ? dto.getPrice() : this.price;
		this.stockQuantity = dto.getStockQuantity() != null ? dto.getStockQuantity() : this.stockQuantity;
		this.isEnable = dto.getIsEnable() != null ? dto.getIsEnable() : this.isEnable;
		this.category = category != null ? category : this.category;
	}
}
