package com.mmd.spring;

import com.mmd.diary.DiaryService;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.entity.Member;
import com.mmd.member.MemberService;
import com.mmd.repository.DiaryRepository;
import com.mmd.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiaryServiceTest { //서비스만 테스트해야하지만.. 일단 통합 테스트를 하는게 어떤지.. 아 ~
    @InjectMocks
    private DiaryService diaryService;

    @Mock
    private DiaryRepository diaryRepository;

    @Mock
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    static void setUp() {
        Member member = Member.of("tester@gmail.com", "password123!@#", "테스터", "테짱", "01023451234", "인천시 계양구");
    }

    @AfterEach
    static void tearDown() {

    }

    @Test
    public void 다이어를_작성한다() {
        // given


        // when
        // then
    }
}
