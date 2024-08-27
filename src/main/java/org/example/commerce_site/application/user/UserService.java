package org.example.commerce_site.application.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.user.dto.UserRequestDTO;
import org.example.commerce_site.application.user.dto.UserResponseDTO;
import org.example.commerce_site.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO.Create create(UserRequestDTO.Create dto) {
        return UserResponseDTO.Create.of(userRepository.save(UserRequestDTO.Create.toEntity(dto)));
    }
}
