package org.example.commerce_site.representation.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.commerce_site.application.user.dto.UserResponseDTO;

public class UserResponse {

    @Getter
    @Builder
    @ToString
    public static class Create {
        private String name;
        private String email;

        public static UserResponse.Create of(UserResponseDTO.Create dto) {
            return UserResponse.Create.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .build();
        }
    }
}
