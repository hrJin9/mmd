package com.mmd.entity;

import com.mmd.domain.CommentVisibility;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OnDelete(action = OnDeleteAction.CASCADE) // hard delete 방지
@SQLDelete(sql = "UPDATE comment SET deleted_date = CURRENT_TIMESTAMP WHERE comment_id = ?") // soft delete
@Where(clause = "deleted_date is null") // delete 되지 않은것만 조회
public class Comment extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private Long groupId;

    private Long level;

    private Long upperId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;

    @Enumerated(EnumType.STRING)
    private CommentVisibility commentVisibility;

    @Builder
    public Comment(Long groupId, Long level, Long upperId, String content, Diary diary, Member writer, CommentVisibility commentVisibility) {
        this.groupId = groupId;
        this.level = level;
        this.upperId = upperId;
        this.content = content;
        this.diary = diary;
        this.writer = writer;
        this.commentVisibility = commentVisibility;
    }
    
    public static Comment createComment(Long groupId, Long level, Long upperId, String content, Diary diary, Member writer, CommentVisibility commentVisibility) {
        return Comment.builder()
                .groupId(groupId)
                .level(level)
                .upperId(upperId)
                .content(content)
                .diary(diary)
                .writer(writer)
                .commentVisibility(commentVisibility).build();
    }
    
    public void updateComment(String content, CommentVisibility commentVisibility) {
        this.content = content;
        this.commentVisibility = commentVisibility;
    }
}
