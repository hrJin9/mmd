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
public class Diary extends CommonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String subject;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DiaryVisibility diaryVisibility = DiaryVisibility.PUBLIC;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus = UseStatus.IN_USE;
}
