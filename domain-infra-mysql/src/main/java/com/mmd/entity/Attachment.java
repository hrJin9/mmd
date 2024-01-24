package com.mmd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Attachment extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String fileName;

    private String originFileName;

    private Long fileSize;

    @Builder
    public Attachment(Diary diary, String fileName, String originFileName, Long fileSize) {
        this.diary = diary;
        this.fileName = fileName;
        this.originFileName = originFileName;
        this.fileSize = fileSize;
    }

    public static Attachment createAttachment(Diary diary, String fileName, String originFileName, Long fileSize) {
        return Attachment.builder()
                .diary(diary)
                .fileName(fileName)
                .originFileName(originFileName)
                .fileSize(fileSize).build();
    }
}
