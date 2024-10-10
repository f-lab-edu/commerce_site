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
@Table(name = "categories")
public class Category extends BaseTimeEntity {
	private String name;
	private String description;
	private Long parentCategoryId;
}
