package org.example.commerce_site.application.partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.partner.dto.PartnerRequestDTO;
import org.example.commerce_site.application.partner.dto.PartnerResponseDTO;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerFacade {

    private final PartnerService partnerService;

    public PartnerResponseDTO.Create create(PartnerRequestDTO.Create dto) {
        return partnerService.create(dto);
    }
}
