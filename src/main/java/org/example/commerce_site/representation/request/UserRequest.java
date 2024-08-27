package org.example.commerce_site.representation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;
import org.example.commerce_site.application.user.dto.UserRequestDTO;

public class UserRequest {
    @Getter
    @ToString
    public static class Create {
        @NotBlank
        private String name;

        @NotBlank
        //TODO email 형식 체크
        private String email;

        @NotBlank
        //TODO 패스워드 형식 체크 (8자리 이상 20자리 이하 영문 + 숫자)
        private String password;

        public static UserRequestDTO.Create toDTO(UserRequest.Create request) {
            return UserRequestDTO.Create.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    //TODO PWD 암호화
                    .password(request.getPassword())
                    .build();
        }
    }
}
