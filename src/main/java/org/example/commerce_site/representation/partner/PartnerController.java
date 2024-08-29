package org.example.commerce_site.representation.partner;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.partner.PartnerService;
import org.example.commerce_site.representation.partner.request.PartnerRequest;
import org.example.commerce_site.representation.partner.response.PartnerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {
    private final PartnerService partnerService;

    @PostMapping()
    public PartnerResponse.Create createPartner(@Valid @RequestBody PartnerRequest.Create request) {
        return PartnerResponse.Create.of(partnerService.create(PartnerRequest.Create.toDTO(request)));
    }
}
