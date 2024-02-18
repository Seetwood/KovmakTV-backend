package dev.vorstu.mappers;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.dto.UserSignUpDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-14T01:26:24+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserSignUpDto toDto(AuthUserEntity source) {
        if ( source == null ) {
            return null;
        }

        UserSignUpDto userSignUpDto = new UserSignUpDto();

        userSignUpDto.setId( source.getId() );
        userSignUpDto.setUsername( source.getUsername() );
        userSignUpDto.setName( source.getName() );
        userSignUpDto.setSurname( source.getSurname() );

        return userSignUpDto;
    }

    @Override
    public void updateUser(UserSignUpDto userSignUpDto, AuthUserEntity user) {
        if ( userSignUpDto == null ) {
            return;
        }

        user.setId( userSignUpDto.getId() );
        user.setUsername( userSignUpDto.getUsername() );
        user.setName( userSignUpDto.getName() );
        user.setSurname( userSignUpDto.getSurname() );
    }
}
