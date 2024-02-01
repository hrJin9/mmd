package com.mmd.attachment;

import com.mmd.attachment.dto.AttachmentDto;
import com.mmd.entity.Attachment;
import com.mmd.entity.Diary;
import com.mmd.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final FileManager fileManager;
    
    /* 파일 저장 */
    public void saveDiaryFiles(Diary diary, AttachmentDto attachmentDto) {
        List<Attachment> attachments = attachmentDto.getFiles().stream()
                .map(file -> Attachment.createAttachment(
                        diary,
                        fileManager.getFileName(file),
                        file.getOriginalFilename(),
                        fileManager.uploadAndGetFilePath(file),
                        file.getSize()
                ))
                .collect(Collectors.toList());

        diary.getAttachments().addAll(attachments);
    }
    
    /* 파일 수정 */
    public void updateDiaryFiles(Diary diary, AttachmentDto attachmentDto) {
        


    }

}
