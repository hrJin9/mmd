package com.mmd.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE image SET deleted_date = CURRENT_TIMESTAMP WHERE image_id = ?") // soft delete
@Where(clause = "deleted_date is null") // delete 되지 않은것만 조회
public class Image extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String fileName;

    @Builder
    public Image(Diary diary, String fileName) {
        this.diary = diary;
        this.fileName = fileName;
    }

    public static Image upload(Diary diary, String fileName) {
        return Image.builder()
                .diary(diary)
                .fileName(fileName)
                .build();
    }
}
