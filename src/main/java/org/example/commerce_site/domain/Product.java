package org.example.commerce_site.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.commerce_site.common.domain.BaseTimeEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@SuperBuilder
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
