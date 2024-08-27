package org.example.commerce_site.representation.partner.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.commerce_site.application.partner.dto.PartnerResponseDTO;

public class PartnerResponse {

    @Getter
    @Builder
    @ToString
    public static class Create {
        private String name;
        private String email;
        private String businessNumber;

        public static PartnerResponse.Create of(PartnerResponseDTO.Create dto) {
            return PartnerResponse.Create.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .businessNumber(dto.getBusinessNumber())
                    .build();
        }
    }
}
