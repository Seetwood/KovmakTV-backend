package dev.vorstu.db.entities.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Текст комментария */
    @Column(columnDefinition = "TEXT")
    private String textComment;

    /** Сущность отправителя */
    @ManyToOne(targetEntity = AuthUserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, insertable = false, updatable = false)
    private AuthUserEntity sender;

    /** Идентификатор отправителя */
    @Column(name = "sender_id")
    private Long senderId;

    /** Сущность рецензии */
    @ManyToOne(targetEntity = Review.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false, insertable = false, updatable = false)
    private Review review;

    /** Идентификатор рецензии */
    @Column(name = "review_id")
    private Long reviewId;

    /** Сущность родителького комментария */
    @ManyToOne(targetEntity = Comment.class)
    @JoinColumn(name = "parent_comment_id", insertable = false, updatable = false)
    private Comment parentComment;

    /** Идентификатор родительского комментария */
    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    /** Список вложенных комментариев */
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> childComments;
}
