package org.example.commerce_site.representation.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.representation.user.request.UserRequest;
import org.example.commerce_site.representation.user.response.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public UserResponse.Create createUser(@Valid @RequestBody UserRequest.Create request) {
        return UserResponse.Create.of(userService.create(UserRequest.Create.toDTO(request)));
    }
}
