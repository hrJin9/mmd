package com.mmd.entity;

import com.mmd.domain.UseStatus;
import com.mmd.domain.Visibility;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends CommonDate {
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
    private Visibility visibility = Visibility.PUBLIC;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus = UseStatus.IN_USE;

    @Builder
    public Comment(Long groupId, Long level, Long upperId, String content, Diary diary, Member writer, Visibility visibility, UseStatus useStatus) {
        this.groupId = groupId;
        this.level = level;
        this.upperId = upperId;
        this.content = content;
        this.diary = diary;
        this.writer = writer;
        this.visibility = visibility;
        this.useStatus = useStatus;
    }
    
    public static Comment createComment(Long groupId, Long level, Long upperId, String content, Diary diary, Member writer, Visibility visibility) {
        return Comment.builder()
                .groupId(groupId)
                .level(level)
                .upperId(upperId)
                .content(content)
                .diary(diary)
                .writer(writer)
                .visibility(visibility).build();
    }
    
    public void updateComment(String content, Visibility visibility) {
        this.content = content;
        this.visibility = visibility;
    }
}
