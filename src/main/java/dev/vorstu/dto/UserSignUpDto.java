package dev.vorstu.dto;

import lombok.Data;
@Data
public class UserSignUpDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
}
