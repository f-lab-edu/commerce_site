package org.example.commerce_site.domain;

import org.example.commerce_site.common.domain.BaseTimeEntity;
import org.hibernate.annotations.JdbcTypeCode;
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

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
