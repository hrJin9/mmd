package com.mmd.comment;

import com.mmd.comment.dto.CommentCreateDto;
import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.comment.dto.CommentUpdateDto;
import com.mmd.diary.DiaryService;
import com.mmd.domain.CommentVisibility;
import com.mmd.entity.Comment;
import com.mmd.entity.Diary;
import com.mmd.entity.Member;
import com.mmd.exception.ContentsNotFoundException;
import com.mmd.exception.MemberNotValidException;
import com.mmd.member.MemberService;
import com.mmd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiaryService diaryService;
    private final MemberService memberService;

    /* 다이어리의 코멘트 조회 */
    @Transactional(readOnly = true)
    public List<CommentFindResultDto> findComments(Long memberId, Long diaryId) {
        // TODO : 다이어리 조회할 수 있는지 체크
        
        // TODO : PUBLIC이면 그냥 조회, FRIEND이면 friend일때만 조회 (PRIVATE은 안됨!!)
        List<Comment> comments = commentRepository.findAllByDiaryId(diaryId);
        return comments.stream()
                .filter(comment -> comment.getCommentVisibility().equals(CommentVisibility.FRIEND) || comment.getCommentVisibility().equals(CommentVisibility.PUBLIC))
                .map(CommentFindResultDto::from)
                .collect(Collectors.toList());
    }

    /* 코멘트 작성 */
    @Transactional
    public Long createComment(CommentCreateDto serviceDto) {
        Member member = memberService.findValidMember(serviceDto.getMemberId());
        Diary diary = diaryService.findDiary(serviceDto.getDiaryId());

        Comment comment = Comment.createComment(
                serviceDto.getGroupId(),
                serviceDto.getLevel(),
                serviceDto.getUpperId(),
                serviceDto.getContent(),
                diary,
                member,
                serviceDto.getCommentVisibility()
        );

        commentRepository.save(comment);
        return comment.getId();
    }

    /* 코멘트 수정 */
    @Transactional
    public void updateComment(CommentUpdateDto serviceDto) {
        Comment comment = findValidCommentById(serviceDto.getCommentId());

        if(!comment.getWriter().getId().equals(serviceDto.getMemberId())) {
            throw new MemberNotValidException("로그인된 사용자의 코멘트가 아닙니다.");
        }

        comment.updateComment(serviceDto.getContent(), serviceDto.getCommentVisibility());
    }

    /* 코멘트 삭제 */
    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = findValidCommentById(commentId);

        if(!comment.getWriter().getId().equals(memberId)) {
            throw new MemberNotValidException("로그인된 사용자의 코멘트가 아닙니다.");
        }

        commentRepository.delete(comment);
    }
    
    // 코멘트 찾기
    public Comment findValidCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ContentsNotFoundException("존재하지 않는 코멘트입니다."));
    }
}
