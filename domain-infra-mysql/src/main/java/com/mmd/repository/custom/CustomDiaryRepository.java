package com.mmd.repository.custom;

import com.mmd.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomDiaryRepository {
    Page<Diary> findAllDiaries(Long loginId, Pageable pageable);
}
