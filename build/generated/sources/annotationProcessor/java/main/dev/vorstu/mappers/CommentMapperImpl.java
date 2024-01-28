package dev.vorstu.mappers;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.dto.CommentDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-25T13:08:54+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto toDto(Comment source) {
        if ( source == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setNameSender( sourceSenderName( source ) );
        commentDto.setSurnameSender( sourceSenderSurname( source ) );
        commentDto.setId( source.getId() );
        commentDto.setReviewId( source.getReviewId() );
        commentDto.setParentCommentId( source.getParentCommentId() );
        commentDto.setTextComment( source.getTextComment() );
        commentDto.setChildComments( commentListToCommentDtoList( source.getChildComments() ) );

        return commentDto;
    }

    private String sourceSenderName(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        AuthUserEntity sender = comment.getSender();
        if ( sender == null ) {
            return null;
        }
        String name = sender.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String sourceSenderSurname(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        AuthUserEntity sender = comment.getSender();
        if ( sender == null ) {
            return null;
        }
        String surname = sender.getSurname();
        if ( surname == null ) {
            return null;
        }
        return surname;
    }

    protected List<CommentDto> commentListToCommentDtoList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto> list1 = new ArrayList<CommentDto>( list.size() );
        for ( Comment comment : list ) {
            list1.add( toDto( comment ) );
        }

        return list1;
    }
}
