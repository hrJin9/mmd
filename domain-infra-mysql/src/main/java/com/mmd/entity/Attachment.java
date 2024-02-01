package com.mmd.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE attachment SET deleted_date = CURRENT_TIMESTAMP WHERE attachment_id = ?") // soft delete
@Where(clause = "deleted_date is null") // delete 되지 않은것만 조회
public class Attachment extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String fileName;

    private String originFileName;

    private String path;

    private Long fileSize;

    @Builder
    public Attachment(Diary diary, String fileName, String originFileName, String path, Long fileSize) {
        this.diary = diary;
        this.fileName = fileName;
        this.originFileName = originFileName;
        this.path = path;
        this.fileSize = fileSize;
    }

    public static Attachment createAttachment(Diary diary, String fileName, String originFileName, String path, Long fileSize) {
        return Attachment.builder()
                .diary(diary)
                .fileName(fileName)
                .originFileName(originFileName)
                .fileSize(fileSize)
                .path(path)
                .build();
    }
}