package dev.vorstu.db.entities.auth;

import javax.persistence.*;

import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.db.enums.RoleUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class AuthUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String surname;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleUser role;


    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public AuthUserEntity(String username, String password, String name, String surname,  RoleUser role) {
        this.password = passwordEncoder.encode(password);
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

}
