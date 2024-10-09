package org.example.commerce_site.domain;

import org.example.commerce_site.attribute.PartnerStatus;
import org.example.commerce_site.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "partners")
public class Partner extends BaseTimeEntity {
	private String name;
	private String authId;
	private String businessNumber;
	private String email;
	@Enumerated(EnumType.STRING)
	private PartnerStatus status;

	public void updateStatus(PartnerStatus partnerStatus) {
		this.status = partnerStatus;
	}

	public void updateAuthId(String authId) {
		this.authId = authId;
	}
}
