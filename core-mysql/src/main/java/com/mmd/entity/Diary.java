package com.mmd.entity;

import com.mmd.domain.DiaryVisibility;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE diary SET deleted_date = CURRENT_TIMESTAMP WHERE diary_id = ?") // soft delete
@Where(clause = "deleted_date is null") // delete 되지 않은것만 조회
public class Diary extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String subject;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL) // 기본 Lazy, cascade = 영속성 함께 관리
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

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
    
    /* 다이어리 수정 */
    public int update(String subject, String contents, DiaryVisibility diaryVisibility) {
        int updateCount = 0;
        if(StringUtils.hasText(subject)) {
            this.subject = subject;
            updateCount++;
        }
        if(StringUtils.hasText(contents)) {
            this.contents = contents;
            updateCount++;
        }
        if(Objects.nonNull(diaryVisibility)) {
            this.diaryVisibility = diaryVisibility;
            updateCount++;
        }
        return updateCount;
    }
}
