package com.mmd.spring;

import com.mmd.common.PageDto;
import com.mmd.diary.DiaryService;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.dto.DiaryFindResultDto;
import com.mmd.domain.Criteria;
import com.mmd.entity.Member;
import com.mmd.member.MemberService;
import com.mmd.repository.DiaryRepository;
import com.mmd.repository.MemberRepository;
import com.mmd.support.SpringServiceTest;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringServiceTest
public class DiaryServiceTest { //서비스만 테스트해야하지만.. 일단 통합 테스트를 하는게 어떤지.. 아 ~
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryRepository diaryRepository;

    @BeforeEach
    static void setUp() {
//        Member member = Member.of("dallae@gmail.com", "member123!@#", "진혜린", "달래", "01023451234", "인천시 계양구");
    }

    @AfterEach
    static void tearDown() {

    }

    @Test
    public void 다른_유저들의_다이어리_목록을_조회한다() {
        // given
        PageDto pageDto = new PageDto(0, 10, Criteria.CREATED_DATE);
        Long loginId = 1L;


        // when
        List<DiaryFindResultDto> diaries = diaryService.findAllDiaries(loginId, pageDto);

        // then


    }
}
