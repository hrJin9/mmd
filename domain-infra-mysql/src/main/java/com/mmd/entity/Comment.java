package com.mmd.entity;

import com.mmd.domain.CommentVisibility;
import com.mmd.domain.UseStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends Common {
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
    public Comment(Long groupId, Long level, Long upperId, String content, Diary diary, Member writer, CommentVisibility commentVisibility, UseStatus useStatus) {
        this.groupId = groupId;
        this.level = level;
        this.upperId = upperId;
        this.content = content;
        this.diary = diary;
        this.writer = writer;
        this.commentVisibility = commentVisibility;
        this.useStatus = useStatus;
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
