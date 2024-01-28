package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class UserService {
    @Autowired
    private  AuthUserRepository authUserRepository;

    public AuthUserEntity getUserByUsername(String username) {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
    }
    public AuthUserEntity findByUserId(Long userId){
        return authUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
    }

    public UserSignUpDto getUser(Authentication authentication) {
        String username = authentication.getName();
        AuthUserEntity currentUser = authUserRepository.findByUsername(username).get();
        UserSignUpDto userDto = new UserSignUpDto();
        userDto.setId(currentUser.getId());
        userDto.setUsername(currentUser.getUsername());
        userDto.setName(currentUser.getName());
        userDto.setSurname(currentUser.getSurname());
        return userDto;
    }

    public UserSignUpDto updateUser(Authentication authentication, UserSignUpDto updateUser) {
        String username = authentication.getName();
        AuthUserEntity user = authUserRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setName(updateUser.getName());
            user.setSurname(updateUser.getSurname());
            authUserRepository.save(user);
            return updateUser;
        }
        return null;
    }
    @Transactional
    public String deleteAccount(Principal user) {
        String username = user.getName();
        authUserRepository.deleteByUsername(username);
        return "Аккаунт успешно удален";
    }
}
