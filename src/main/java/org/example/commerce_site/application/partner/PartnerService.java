package org.example.commerce_site.application.partner;

import org.example.commerce_site.application.partner.dto.PartnerRequestDto;
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

	@Transactional(readOnly = true)
	public Partner getPartner(String partnerAuthId) {
		return partnerRepository.findByAuthId(partnerAuthId).orElseThrow(
			() -> new CustomException(ErrorCode.PARTNER_NOT_FOUND)
		);
	}
}
