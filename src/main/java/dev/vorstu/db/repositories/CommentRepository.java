package dev.vorstu.db.repositories;

import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.entities.reviews.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

}