package dev.vorstu.mappers;

import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {

    CommentMapper MAPPER = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "sender.name", target = "nameSender")
    @Mapping(source = "sender.surname", target = "surnameSender")
    CommentDto toDto(Comment source);

}