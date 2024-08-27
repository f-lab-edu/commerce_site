package org.example.commerce_site.representation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.user.UserFacade;
import org.example.commerce_site.application.user.dto.UserResponseDTO;
import org.example.commerce_site.representation.request.UserRequest;
import org.example.commerce_site.representation.request.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserFacade userFacade;

    @PostMapping()
    public UserResponse.Create createUser(@Valid @RequestBody UserRequest.Create request) {
        UserResponseDTO.Create result = userFacade.create(UserRequest.Create.toDTO(request));
        return UserResponse.Create.of(result);
    }
}
