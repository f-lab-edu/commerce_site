package org.example.commerce_site.application.partner;

import org.example.commerce_site.application.partner.dto.PartnerRequestDto;
import org.example.commerce_site.application.partner.dto.PartnerResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.infrastructure.partner.PartnerRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
	private final PartnerRepository partnerRepository;

	@Transactional
	public PartnerResponseDto.Create create(PartnerRequestDto.Create dto) {
		// TODO : email 중복 체크
		return PartnerResponseDto.Create.of(partnerRepository.save(PartnerRequestDto.Create.toEntity(dto)));
	}

	public Partner getPartner(Long partnerId) {
		return partnerRepository.findById(partnerId).orElseThrow(
			() -> new CustomException(ErrorCode.PARTNER_NOT_FOUND)
		);
	}
}
