package com.mmd.repository.custom;

import com.mmd.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomDiaryRepository {
    Page<Diary> findOthersDiaries(Long loginId, Pageable pageable);

    Page<Diary> findOtherDiaries(Long loginId, Long memberId, Pageable pageable);

//    Optional<Diary> findOtherOneDiary(Long loginId, Long memberId, Long diaryId);
}
