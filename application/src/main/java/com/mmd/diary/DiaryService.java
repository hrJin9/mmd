package com.mmd.diary;

import com.mmd.entity.Diary;
import com.mmd.exception.MemberNotFoundException;
import com.mmd.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    
    /* 다이어리 조회 */
    @Transactional(readOnly = true)
    public Diary findDiary(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 다이어리입니다."));
    }
}
