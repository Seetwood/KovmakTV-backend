package dev.vorstu.mappers;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.dto.GenreDto;
import dev.vorstu.dto.UserSignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserSignUpDto toDto(AuthUserEntity source);
}
