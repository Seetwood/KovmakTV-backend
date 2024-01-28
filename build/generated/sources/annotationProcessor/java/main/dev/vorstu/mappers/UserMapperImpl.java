package dev.vorstu.mappers;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.dto.UserSignUpDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-22T01:21:30+0300",
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
        userSignUpDto.setPassword( source.getPassword() );
        userSignUpDto.setName( source.getName() );
        userSignUpDto.setSurname( source.getSurname() );

        return userSignUpDto;
    }
}
