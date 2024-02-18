package dev.vorstu.mappers;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.dto.ReviewDto;
import dev.vorstu.dto.UserSignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "password", ignore = true)
    UserSignUpDto toDto(AuthUserEntity source);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUser(UserSignUpDto userSignUpDto, @MappingTarget AuthUserEntity user);
}