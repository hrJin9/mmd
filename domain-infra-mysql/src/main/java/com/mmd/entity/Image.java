package com.mmd.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE attachment SET deleted_date = CURRENT_TIMESTAMP WHERE image_id = ?") // soft delete
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

    private String originalFileName;

    private String type;

    private String path;

    @Lob
    @Column(length = 1000)
    private byte[] data;

    @Builder
    public Image(Diary diary, String fileName, String originalFileName, String type, String path, byte[] data) {
        this.diary = diary;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.type = type;
        this.path = path;
        this.data = data;
    }


    public static Image createAttachment(Diary diary, String fileName, String originalFileName, String type, String path, byte[] data) {
        return Image.builder()
                .diary(diary)
                .fileName(fileName)
                .originalFileName(originalFileName)
                .type(type)
                .path(path)
                .data(data)
                .build();
    }
}
