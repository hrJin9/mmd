package com.mmd.entity;

import com.mmd.domain.UseStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends CommonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member writer;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

    @Builder
    public Comment(String contents, Diary diary, Member writer, UseStatus useStatus) {
        this.contents = contents;
        this.diary = diary;
        this.writer = writer;
        this.useStatus = useStatus;
    }

}
