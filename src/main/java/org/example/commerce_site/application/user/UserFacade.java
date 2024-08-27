package org.example.commerce_site.application.user;

import lombok.RequiredArgsConstructor;
import org.example.commerce_site.application.user.dto.UserRequestDTO;
import org.example.commerce_site.application.user.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserResponseDTO.Create create(UserRequestDTO.Create dto) {
        return userService.create(dto);
    }
}
