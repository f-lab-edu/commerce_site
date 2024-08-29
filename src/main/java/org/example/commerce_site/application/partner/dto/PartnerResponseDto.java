package org.example.commerce_site.application.partner.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.commerce_site.domain.Partner;

public class PartnerResponseDto {
    @Builder
    @Getter
    @ToString
    public static class Create {
        private String name;
        private String email;
        private String businessNumber;

        public static PartnerResponseDto.Create of(Partner partner) {
            return Create.builder()
                    .name(partner.getName())
                    .email(partner.getEmail())
                    .businessNumber(partner.getBusinessNumber())
                    .build();
        }
    }
}
