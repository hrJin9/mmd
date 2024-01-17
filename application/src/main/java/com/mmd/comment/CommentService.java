package com.mmd.comment;

import com.mmd.comment.dto.CommentCreateDto;
import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.diary.DiaryService;
import com.mmd.entity.Comment;
import com.mmd.entity.Diary;
import com.mmd.entity.Member;
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

    @Transactional(readOnly = true)
    public List<CommentFindResultDto> getComments(Long diaryId) {
        List<Comment> comments = commentRepository.findAllComment(diaryId);
        return comments.stream()
                .map(CommentFindResultDto::from)
                .collect(Collectors.toList());
    }

    public Long createComment(CommentCreateDto serviceDto) {
        Member member = memberService.findMember(serviceDto.getMemberId());
        Diary diary = diaryService.findDiary(serviceDto.getDiaryId());

        Comment comment = Comment.createComment(
                serviceDto.getGroupId(),
                serviceDto.getLevel(),
                serviceDto.getUpperId(),
                serviceDto.getContents(),
                diary,
                member,
                serviceDto.getVisibility()
        );

        commentRepository.save(comment);
        return comment.getId();
    }
}
