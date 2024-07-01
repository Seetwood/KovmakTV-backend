package dev.vorstu.db.services.films;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.enums.RoleUser;
import dev.vorstu.db.repositories.AuthUserRepository;
import dev.vorstu.dto.UserSignUpDto;
import dev.vorstu.exception.AlreadyExistsException;
import dev.vorstu.exception.BusinessException;
import dev.vorstu.exception.NotFoundException;
import dev.vorstu.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

/**
 * Сервис для взаимодействия с данными пользователя, его аккаунтом
 */

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final AuthUserRepository authUserRepository;

    public AuthUserEntity getUserByUsername(String username) {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
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
            AuthUserEntity user = AuthUserEntity.builder()
                    .username(newUser.getUsername())
                    .password(newUser.getPassword())
                    .name(newUser.getName())
                    .surname(newUser.getSurname())
                    .role(RoleUser.USER)
                    .build();
            authUserRepository.save(user);
            log.info("Аккаунт успешно создан: {}", newUser.getUsername());
            return UserMapper.MAPPER.toDto(user);
        }
        catch (AlreadyExistsException e) {
            log.error("пользователь с таким именем уже существует: {}", newUser.getUsername());
            throw new AlreadyExistsException("пользователь с таким именем уже существует.", e.getMessage());
        }
    }

    /**
     * Изменение данных пользователя
     * @param authentication -
     * @param updateUser - Новые данные пользователя
     * @return Пользователь
     */
    public UserSignUpDto updateUser(Authentication authentication, UserSignUpDto updateUser) {
        try {
            AuthUserEntity user = getUserByUsername(authentication.getName());
            UserMapper.MAPPER.updateUser(updateUser, user);
            authUserRepository.save(user);
        }
        catch (BusinessException e) {
            log.error("Ошибка при обновлении информации аккаунта: {}", updateUser);
            throw new BusinessException("Ошибка при обновлении информации аккаунта: ", e.getMessage());
        }
        return updateUser;
    }

    /**
     * Удаление аккаунта пользоателя
     * @param user - Данные о текущей авторизации
     */
    @Transactional
    public void deleteUser(Principal user) {
        String username = user.getName();
        try {
            authUserRepository.deleteByUsername(username);
            log.info("Аккаунт {} успешно удален.", username);
        }
        catch (BusinessException e) {
            log.error("Ошибка при удалении аккаунта: {}", username);
            throw new BusinessException("Ошибка при удалении аккаунта: ", e.getMessage());
        }
    }
}
