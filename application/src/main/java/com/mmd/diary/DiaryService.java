package com.mmd.diary;

import com.mmd.diary.dto.DiaryAttachmentDto;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.entity.Attachment;
import com.mmd.entity.Diary;
import com.mmd.entity.Member;
import com.mmd.exception.ContentsNotFoundException;
import com.mmd.exception.MemberNotFoundException;
import com.mmd.member.MemberService;
import com.mmd.repository.DiaryRepository;
import com.mmd.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberService memberService;
    private final FileManager fileManager;
    
    /* 다이어리 조회 */
    @Transactional(readOnly = true)
    public Diary findDiary(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 다이어리입니다."));
    }

    /* 다이어리 작성 */
    @Transactional
    public Long createDiary(DiaryCreateDto diaryCreateDto, DiaryAttachmentDto diaryAttachmentDto) {
        // 다이어리 저장
        Member member = memberService.findValidMember(diaryCreateDto.getMemberId());
        Diary diary = Diary.createDiary(
                diaryCreateDto.getSubject(),
                diaryCreateDto.getContents(),
                member,
                diaryCreateDto.getDiaryVisibility());
        diaryRepository.save(diary);
        
        // 다이어리 첨부파일 저장
        if(!CollectionUtils.isEmpty(diaryAttachmentDto.getFiles())) {
            List<Attachment> attachments = diaryAttachmentDto.getFiles().stream()
                    .map(file -> Attachment.createAttachment(
                            diary,
                            fileManager.uploadFile(file),
                            file.getOriginalFilename(),
                            file.getSize()
                    ))
                    .collect(Collectors.toList());

            diary.getAttachments().addAll(attachments);
        }

        return diary.getId();
    }
    

    public Diary findValidDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ContentsNotFoundException("존재하지 않는 다이어리입니다."));
    }

}
