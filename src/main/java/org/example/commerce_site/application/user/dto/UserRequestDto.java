package org.example.commerce_site.application.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.commerce_site.attribute.UserStatus;
import org.example.commerce_site.domain.User;

public class UserRequestDto {
    @Getter
    @Builder
    @ToString
    public static class Create {
        private String name;
        private String email;
        private String password;

        public static User toEntity(UserRequestDto.Create dto) {
            return User.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .status(UserStatus.ACTIVE)
                    .build();
        }
    }
}
