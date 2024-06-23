package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.enums.RoleUser;
import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.dto.UserSignUpDto;
import dev.vorstu.exception.AlreadyExistsException;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

/**
 * Сервис для взаимодействия с данными пользователя, его аккаунтом
 */

@Slf4j
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

    /**
     * Создание нового пользователя
     * @param newUser - Данные о новом пользователе
     * @return пользователь
     */
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
            log.error("пользователь с таким именем уже существует: {}", newUser.getUsername());
            throw new AlreadyExistsException("пользователь с таким именем уже существует");
        }
    }

    /**
     * Изменение данных пользователя
     * @param authentication -
     * @param updateUser - Новые данные пользователя
     * @return Пользователь
     */
    public UserSignUpDto updateUser(Authentication authentication, UserSignUpDto updateUser) {
        AuthUserEntity user = getUserByUsername(authentication.getName());
        UserMapper.MAPPER.updateUser(updateUser, user);
        authUserRepository.save(user);
        return updateUser;
    }

    /**
     * Удаление аккаунта пользоателя
     * @param user - Данные о текущей авторизации
     */
    @Transactional
    public void deleteUser(Principal user) {
        String username = user.getName();
        authUserRepository.deleteByUsername(username);
    }
}
