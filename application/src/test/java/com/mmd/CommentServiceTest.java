package com.mmd;

import com.mmd.comment.CommentService;
import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.domain.MemberRole;
import com.mmd.domain.UseStatus;
import com.mmd.entity.Member;
import com.mmd.repository.CommentRepository;
import com.mmd.support.ServiceTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("코멘트 서비스 테스트")
@ServiceTest
class CommentServiceTest {
    private static Member member;

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeAll
    static void setUp() {
        member = new Member(1L, "tester@gmail.com", "password123!@#", "테스터", "테스터닉네임", "01011112222", "테스터 주소", MemberRole.USER, UseStatus.IN_USE);
    }


    @Test
    public void 해당_다이어리의_코멘트를_가져온다() {
        // given
        final Long DIARY_ID = 1L;

//        given(commentRepository.findAllByDiaryId(any()))
//                .willReturn();

        // when
//        List<CommentFindResultDto> comments = commentService.getComments(DIARY_ID);

        // then

    }

}