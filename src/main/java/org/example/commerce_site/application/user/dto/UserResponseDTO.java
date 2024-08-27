package org.example.commerce_site.application.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.commerce_site.domain.User;

public class UserResponseDTO {
    @Builder
    @Getter
    @ToString
    public static class Create {
        private String name;
        private String email;

        public static UserResponseDTO.Create of(User user) {
            return Create.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
    }
}
