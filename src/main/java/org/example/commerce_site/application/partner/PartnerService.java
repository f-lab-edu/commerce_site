package org.example.commerce_site.application.partner;

import org.example.commerce_site.application.partner.dto.PartnerRequestDto;
import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.attribute.PartnerStatus;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.infrastructure.partner.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
	private final PartnerRepository partnerRepository;

	@Transactional
	public Partner create(PartnerRequestDto.CreateWebHook dto) {
		return partnerRepository.save(PartnerRequestDto.CreateWebHook.toEntity(dto));
	}

	@Transactional(readOnly = true)
	public Partner getPartner(Long partnerId) {
		return partnerRepository.findById(partnerId).orElseThrow(
			() -> new CustomException(ErrorCode.PARTNER_NOT_FOUND)
		);
	}

	@Transactional
	public void updatePartnerStatusActive(UserRequestDto.Create dto) {
		Partner partner = partnerRepository.findByEmail(dto.getEmail()).orElseThrow(
			() -> new CustomException(ErrorCode.PARTNER_NOT_FOUND)
		);
		partner.updateStatus(PartnerStatus.ACTIVE);
		partnerRepository.save(partner);
	}

	@Transactional
	public void updateAuthId(Partner partner, String authId) {
		partner.updateAuthId(authId);
		partner.updateStatus(PartnerStatus.ACTIVE);
		partnerRepository.save(partner);
	}
}
