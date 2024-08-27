package org.example.commerce_site.application.partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.partner.dto.PartnerRequestDTO;
import org.example.commerce_site.application.partner.dto.PartnerResponseDTO;
import org.example.commerce_site.infrastructure.PartnerRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public PartnerResponseDTO.Create create(PartnerRequestDTO.Create dto) {
        return PartnerResponseDTO.Create.of(partnerRepository.save(PartnerRequestDTO.Create.toEntity(dto)));
    }
}
