package org.example.commerce_site.domain;

import org.example.commerce_site.common.domain.BaseTimeEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
@SQLDelete(sql = "UPDATE addresses SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted IS FALSE")
@Table(name = "addresses")
public class Address extends BaseTimeEntity {
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;
	private String phoneNumber;
	private String addressType;
	private Boolean isPrimary;
	private String postalCode;
	private String roadAddress;
	private String jibunAddress;
	private String roadNameCode;
	private String buildingName;
	private String addressDetail;

	public void updatePrimary() {
		this.isPrimary = Boolean.FALSE;
	}
}
