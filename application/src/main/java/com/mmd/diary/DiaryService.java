package com.mmd.diary;

import com.mmd.common.PageDto;
import com.mmd.diary.dto.DiaryAttachmentDto;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.dto.DiaryFindResultDto;
import com.mmd.diary.dto.DiaryUpdateDto;
import com.mmd.domain.DiaryVisibility;
import com.mmd.entity.Attachment;
import com.mmd.entity.Diary;
import com.mmd.entity.Member;
import com.mmd.exception.ContentsNotFoundException;
import com.mmd.exception.MemberNotValidException;
import com.mmd.friend.FriendService;
import com.mmd.member.MemberService;
import com.mmd.repository.DiaryRepository;
import com.mmd.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final FriendService friendService;
    private final MemberService memberService;
    private final FileManager fileManager;

    /* 모든 다이어리 목록 조회 */
    @Transactional(readOnly = true)
    public List<DiaryFindResultDto> findAllDiaries(Long loginId, PageDto pageDto) {
        Page<Diary> diaryPage = diaryRepository.findOthersDiaries(loginId, pageDto.toPageable());
        List<Diary> diaries = diaryPage.getContent();
        int totalPages = diaryPage.getTotalPages();

        return diaries.stream()
                .map(diary -> DiaryFindResultDto.of(diary, totalPages))
                .collect(Collectors.toList());
    }

    /* 특정 멤버의 다이어리 작성 목록 조회 */
    @Transactional(readOnly = true)
    public List<DiaryFindResultDto> findMemberDiaries(Long loginId, Long memberId, PageDto pageDto) {
        // FRIEND, PUBLIC인 다이어리이거나 자신일 경우 PRIVATE까지 함께 조회한다.
        Page<Diary> diaryPage;

        if(loginId.equals(memberId)) { // 자신의 다이어리 목록을 조회
            diaryPage = diaryRepository.findAllByWriterId(loginId, pageDto.toPageable());
        } else { // 타인의 다이어리 목록을 조회
            diaryPage = diaryRepository.findOtherDiaries(loginId, memberId, pageDto.toPageable());
        }

        List<Diary> diaries = diaryPage.getContent();
        int totalPages = diaryPage.getTotalPages();

        return diaries.stream()
                .map(diary -> DiaryFindResultDto.of(diary, totalPages))
                .collect(Collectors.toList());
    }

    /* 특정 다이어리 조회 */
    @Transactional(readOnly = true)
    public DiaryFindResultDto findOneDiary(Long loginId, Long memberId, Long diaryId) {
        // 접근할 수 있는 다이어리인지 알아본다.
        Diary diary =  findValidDiaryById(diaryId);

        if(!diary.getDiaryVisibility().equals(DiaryVisibility.PUBLIC) && !loginId.equals(memberId)) { // 접근제한이 있는 타인의 글을 조회하는 경우
            if(diary.getDiaryVisibility().equals(DiaryVisibility.PRIVATE)) {
                throw new MemberNotValidException("접근할 수 없는 다이어리입니다.");
            } else if(diary.getDiaryVisibility().equals(DiaryVisibility.FRIEND) && !friendService.findExistFriend(loginId, memberId)){ // 친구글일 때, 친구가 아닐 경우
                throw new MemberNotValidException("다이어리 작성자와 친구가 아닙니다.");
            }
        }

        return DiaryFindResultDto.from(diary);
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
        if(!CollectionUtils.isEmpty(diaryAttachmentDto.getFiles())) { // 첨부파일이 있는 경우
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
    
    /* 다이어리 수정 */
    @Transactional
    public void updateDiary(Long loginId, DiaryUpdateDto diaryUpdateDto, DiaryAttachmentDto diaryAttachmentDto) {
        // 다이어리 조회
        Diary diary = this.findValidDiaryById(diaryUpdateDto.getDiaryId());

        if(!diary.getWriter().getId().equals(loginId)) {
            throw new MemberNotValidException("로그인한 사용자의 다이어리가 아닙니다.");
        }

        // 기존에 있는 다이어리의 첨부파일을 가져온다.
        List<Attachment> files = diary.getAttachments();
        List<MultipartFile> newFiles = diaryAttachmentDto.getFiles();

        List<Attachment> newAttachments = new ArrayList<>();
        List<Attachment> deleteAttachments = new ArrayList<>();
        if(!CollectionUtils.isEmpty(newFiles)) { // 첨부파일이 있는 경우
            if(CollectionUtils.isEmpty(files)) { // 기존의 첨부파일이 있는 경우
                for(int i=0; i<newFiles.size(); i++) {
                    MultipartFile newFile = newFiles.get(i);
                    Attachment file = files.get(i);

                    String uploadFileName = fileManager.uploadFile(newFile);
                    if(file.getFileName().equals(uploadFileName)) { // 저장된 첨부파일의 이름이 같다면 삭제한다
                        deleteAttachments.add(file);
                    } else {
                        Attachment attachment = Attachment.createAttachment(
                                diary,
                                uploadFileName,
                                newFile.getOriginalFilename(),
                                newFile.getSize()
                        );
                        newAttachments.add(attachment);
                    }
                }
            }

            deleteAttachments.forEach(files::remove);
            files.addAll(newAttachments);
        }

    }

    /* 다이어리 삭제 */
    @Transactional
    public void deleteDiary(Long loginId, Long diaryId) {
        Diary diary = findValidDiaryById(diaryId);

        if(!diary.getWriter().getId().equals(loginId)) {
            throw new MemberNotValidException("로그인한 사용자의 다이어리가 아닙니다.");
        }

        diaryRepository.delete(diary);
    }

    public Diary findValidDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ContentsNotFoundException("존재하지 않는 다이어리입니다."));
    }

}
