package dev.vorstu.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Status;
import javax.transaction.Transactional;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.enums.RoleUser;
import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.db.services.films.UserService;
import dev.vorstu.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static dev.vorstu.db.config.SecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping("currentUser")
    @ResponseBody
    public Principal user(Principal user) {

        log.warn("getUserAuth: " + (user != null ? user.getName() : "null"));
        return user;
    }

    @PostMapping(path = "logout", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Principal logout(Principal user, HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
        return user;
    }
    @PostMapping("register")
    public UserSignUpDto registerUser(@RequestBody UserSignUpDto newUser) {
        return userService.createUser(newUser);
    }
}
