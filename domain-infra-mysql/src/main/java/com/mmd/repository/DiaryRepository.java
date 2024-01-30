package com.mmd.repository;

import com.mmd.entity.Diary;
import com.mmd.repository.custom.CustomDiaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>, CustomDiaryRepository {
    Page<Diary> findAllByWriterId(Long writerId, Pageable pageable);

    Optional<Diary> findByIdAndWriterId(Long diaryId, Long writerId);
}
