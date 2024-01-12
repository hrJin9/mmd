package com.mmd.entity;

import com.mmd.domain.UseStatus;
import com.mmd.domain.Visibility;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Diary extends CommonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryNo;

    private String subject;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member writer;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

}
