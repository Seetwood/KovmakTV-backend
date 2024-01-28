package dev.vorstu.controllers;

import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/profile")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserSignUpDto getUser(Authentication authentication) {
        return userService.getUser(authentication);
    }
    @PutMapping("/user")
    public UserSignUpDto updateDataUser(Authentication authentication, @RequestBody UserSignUpDto updateUser) {
        return userService.updateUser(authentication, updateUser);
    }
    @DeleteMapping("/user")
    public String deleteAccount(Principal user) {
        return userService.deleteAccount(user);
    }
}