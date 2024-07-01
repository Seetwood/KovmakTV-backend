package dev.vorstu.db.entities.auth;

import javax.persistence.*;

import dev.vorstu.db.enums.RoleUser;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Table(name="users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserEntity {
    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Логин */
    @NonNull
    @Column(unique=true)
    private String username;

    /** Имя */
    private String name;

    /** Фамилия */
    private String surname;

    /** Пароль */
    private String password;

    /** Роль пользователя */
    @Enumerated(EnumType.STRING)
    private RoleUser role;

    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
}
