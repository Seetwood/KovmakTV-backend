package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.enums.RoleUser;
import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.dto.UserSignUpDto;
import dev.vorstu.exception.AlreadyExistsException;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.FilmMapper;
import dev.vorstu.mappers.UserMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
                .orElseThrow(() -> new NotFoundException("User is not found"));
    }

    public UserSignUpDto getUser(Authentication  authentication) {
        String username = authentication.getName();
        return UserMapper.MAPPER.toDto(getUserByUsername(username));
    }

    public UserSignUpDto createUser(UserSignUpDto newUser) {
        try {
            AuthUserEntity user = new AuthUserEntity(
                    newUser.getUsername(),
                    newUser.getPassword(),
                    newUser.getName(),
                    newUser.getSurname(),
                    RoleUser.USER
            );
            authUserRepository.save(user);
            return UserMapper.MAPPER.toDto(user);
        }
        catch (ConstraintViolationException | DataIntegrityViolationException e) {
            throw new AlreadyExistsException("username already exists");
        }
    }

    public UserSignUpDto updateUser(Authentication authentication, UserSignUpDto updateUser) {
        AuthUserEntity user = getUserByUsername(authentication.getName());
        UserMapper.MAPPER.updateUser(updateUser, user);
        authUserRepository.save(user);
        return updateUser;
    }
    @Transactional
    public void deleteUser(Principal user) {
        String username = user.getName();
        authUserRepository.deleteByUsername(username);
    }
}
