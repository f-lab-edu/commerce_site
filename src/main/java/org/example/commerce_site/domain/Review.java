package org.example.commerce_site.domain;

import org.example.commerce_site.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review extends BaseTimeEntity {
	private Long userId;
	private Long productId;
	private String comment;
	private Integer rating;
}
