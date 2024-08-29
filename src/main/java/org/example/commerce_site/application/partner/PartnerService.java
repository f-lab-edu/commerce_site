package org.example.commerce_site.application.partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.partner.dto.PartnerRequestDto;
import org.example.commerce_site.application.partner.dto.PartnerResponseDto;
import org.example.commerce_site.infrastructure.PartnerRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public PartnerResponseDto.Create create(PartnerRequestDto.Create dto) {
        return PartnerResponseDto.Create.of(partnerRepository.save(PartnerRequestDto.Create.toEntity(dto)));
    }
}
