package dev.vorstu.controllers;

import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.UserSignUpDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Контроллер, предназначенный для возаимодействия пользоателя с аккаунтом
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/profile")
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public UserSignUpDto getUser(Authentication authentication) {
        return userService.getUser(authentication);
    }
    @PutMapping("/user")
    public UserSignUpDto updateDataUser(Authentication authentication, @RequestBody UserSignUpDto updateUser) {
        return userService.updateUser(authentication, updateUser);
    }
    @DeleteMapping("/user")
    public void deleteAccount(Principal user) {
        userService.deleteUser(user);
    }
}