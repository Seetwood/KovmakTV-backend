package dev.vorstu.db.entities.reviews;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String textComment;

    @ManyToOne(targetEntity = AuthUserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, insertable = false, updatable = false)
    private AuthUserEntity sender;
    @Column(name = "sender_id")
    private Long senderId;

    @ManyToOne(targetEntity = Review.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false, insertable = false, updatable = false)
    private Review review;
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(targetEntity = Comment.class)
    @JoinColumn(name = "parent_comment_id", insertable = false, updatable = false)
    private Comment parentComment;
    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> childComments;
    public Comment(Long senderId, Long reviewId,String textComment, Long parentCommentId) {
        this.senderId = senderId;
        this.reviewId = reviewId;
        this.textComment = textComment;
        this.parentCommentId = parentCommentId;
    }
}
