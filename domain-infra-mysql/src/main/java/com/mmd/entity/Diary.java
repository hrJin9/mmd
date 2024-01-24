package com.mmd.entity;

import com.mmd.domain.UseStatus;
import com.mmd.domain.DiaryVisibility;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Diary extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String subject;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL) // 기본 Lazy
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DiaryVisibility diaryVisibility;

    @Builder
    public Diary(String subject, String contents, Member writer, DiaryVisibility diaryVisibility) {
        this.subject = subject;
        this.contents = contents;
        this.writer = writer;
        this.diaryVisibility = diaryVisibility;
    }

    public static Diary createDiary(String subject, String contents, Member writer, DiaryVisibility diaryVisibility) {
        return Diary.builder()
                .subject(subject)
                .contents(contents)
                .writer(writer)
                .diaryVisibility(diaryVisibility).build();
    }
}
