package com.mmd;

import com.mmd.domain.FriendStatus;
import com.mmd.domain.MemberRole;
import com.mmd.domain.UseStatus;
import com.mmd.entity.Friend;
import com.mmd.entity.Member;
import com.mmd.exception.BadRequestException;
import com.mmd.exception.ContentsNotFoundException;
import com.mmd.exception.MemberNotValidException;
import com.mmd.friend.FriendService;
import com.mmd.friend.dto.FriendFindResultDto;
import com.mmd.member.MemberService;
import com.mmd.repository.FriendRepository;
import com.mmd.support.ServiceTest;
import com.mmd.vo.FriendFindResultVO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("친구 서비스 테스트")
@ServiceTest
public class FriendServiceTest {
    private static Member member;
    private static Member friendMember1;
    private static Member friendMember2;

    @InjectMocks
    private FriendService friendService;

    @Mock
    private FriendRepository friendRepository;

    @Mock
    private MemberService memberService;

    @BeforeAll
    static void setUp() {
        member = new Member(1L, "tester@gmail.com", "password123!@#", "테스터", "테스터닉네임", "01011112222", "테스터 주소", MemberRole.USER, UseStatus.IN_USE);
        friendMember1 = new Member(2L, "friend1@gmail.com", "password123!@#", "친구1", "친구1", "01011112222", "테스터 주소", MemberRole.USER, UseStatus.IN_USE);
        friendMember2 = new Member(3L, "friend2@gmail.com", "password123!@#", "친구2", "친구1", "01011112222", "테스터 주소", MemberRole.USER, UseStatus.IN_USE);
    }

    @Test
    public void 친구를_조회한다() {
        // given
        Friend friend1 = new Friend(member, friendMember1, FriendStatus.Y, UseStatus.IN_USE);
        Friend friend2 = new Friend(friendMember2, member, FriendStatus.Y, UseStatus.IN_USE);

        given(friendRepository.findAllFriends(member.getId()))
                .willReturn(List.of(friend1, friend2));
        // when
        List<FriendFindResultDto> results = friendService.findFriends(member.getId());

        // then
        assertThat(results.get(0).getFriendId()).isEqualTo(friend1.getId());
        assertThat(results.get(1).getFriendId()).isEqualTo(friend2.getId());
    }

    @Test
    public void 친구신청을_조회한다() {
        // given
        Friend friendRequest = new Friend(friendMember1, member, FriendStatus.IN_PROGRESS, UseStatus.IN_USE);
        given(friendRepository.findAllFriendRequests(member.getId()))
                .willReturn(List.of(new FriendFindResultVO(friendRequest.getId(), friendRequest.getRequester())));

        // when
        List<FriendFindResultDto> results = friendService.findFriendRequests(member.getId());

        // then
        assertThat(results.get(0).getFriendId()).isEqualTo(friendRequest.getId());
    }

    @Test
    public void 새로운_친구신청을_성공한다() {
        given(memberService.findValidMember(member.getId()))
                .willReturn(member);
        given(memberService.findValidMember(friendMember1.getId()))
                .willReturn(friendMember1);
        given(friendRepository.findByRequesterAndRespondent(member, friendMember1))
                .willReturn(Optional.empty());

        // when
        friendService.createFriendRequest(member.getId(), friendMember1.getId());

        // then
        then(friendRepository).should(times(1)).save(any());
    }

    @Test
    public void 친구신청시_내역이_있는_경우_기존의_내역을_가져와서_상태를_변경한다() {
        // given
        Friend friendRespondent = new Friend(member, friendMember1, FriendStatus.Y, UseStatus.DELETED);
        given(memberService.findValidMember(member.getId()))
                .willReturn(member);
        given(memberService.findValidMember(friendMember1.getId()))
                .willReturn(friendMember1);
        given(friendRepository.findByRequesterAndRespondent(member, friendMember1))
                .willReturn(Optional.of(friendRespondent));

        // when
        friendService.createFriendRequest(member.getId(), friendMember1.getId());

        // then
        assertThat(friendRespondent.getFriendStatus()).isEqualTo(FriendStatus.IN_PROGRESS);
        assertThat(friendRespondent.getUseStatus()).isEqualTo(UseStatus.IN_USE);
    }

    @Test
    public void 친구신청시_이미_친구인_경우_예외를_던진다() {
        // given
        Friend friend = new Friend(member, friendMember1, FriendStatus.Y, UseStatus.IN_USE);

        given(memberService.findValidMember(member.getId()))
                .willReturn(member);
        given(memberService.findValidMember(friendMember1.getId()))
                .willReturn(friendMember1);
        given(friendRepository.findByRequesterAndRespondent(member, friendMember1))
                .willReturn(Optional.of(friend));

        // when, then
        assertThatThrownBy(() -> friendService.createFriendRequest(member.getId(), friendMember1.getId()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("이미 친구인 회원입니다.");
    }

    @Test
    public void 친구신청을_취소한다() {
        // given
        Friend friendRequest = new Friend(member, friendMember1, FriendStatus.IN_PROGRESS, UseStatus.IN_USE);
        given(friendRepository.findByIdAndFriendStatusAndUseStatus(friendRequest.getId(), FriendStatus.IN_PROGRESS, UseStatus.IN_USE))
                .willReturn(Optional.of(friendRequest));

        // when
        friendService.deleteFriendRequest(member.getId(), friendRequest.getId());

        // then
        assertThat(friendRequest.getUseStatus()).isEqualTo(UseStatus.DELETED);
    }

    @Test
    public void 친구신청을_취소할_때_친구신청자와_로그인한사용자의_정보가다르면_예외를_던진다() {
        //given
        Friend friendRequest = new Friend(friendMember2, friendMember1, FriendStatus.IN_PROGRESS, UseStatus.IN_USE);
        given(friendRepository.findByIdAndFriendStatusAndUseStatus(friendRequest.getId(), FriendStatus.IN_PROGRESS, UseStatus.IN_USE))
                .willReturn(Optional.of(friendRequest));

        // when, then
        assertThatThrownBy(() -> friendService.deleteFriendRequest(member.getId(), friendRequest.getId()))
                .isInstanceOf(MemberNotValidException.class)
                .hasMessage("로그인한 사용자가 신청한 내역이 아닙니다.");
    }

    @Test
    public void 친구신청을_수락한다() {
        // given
        Friend friendRequest = new Friend(friendMember1, member, FriendStatus.IN_PROGRESS, UseStatus.IN_USE);

        given(friendRepository.findByIdAndFriendStatusAndUseStatus(friendRequest.getId(), FriendStatus.IN_PROGRESS, UseStatus.IN_USE))
                .willReturn(Optional.of(friendRequest));

        // when
        friendService.updateFriendRequest(member.getId(), friendRequest.getId(), FriendStatus.Y);

        // then
        assertThat(friendRequest.getFriendStatus()).isEqualTo(FriendStatus.Y);
    }

    @Test
    public void 친구신청을_거절한다() {
        // given
        Friend friendRequest = new Friend(friendMember1, member, FriendStatus.IN_PROGRESS, UseStatus.IN_USE);

        given(friendRepository.findByIdAndFriendStatusAndUseStatus(friendRequest.getId(), FriendStatus.IN_PROGRESS, UseStatus.IN_USE))
                .willReturn(Optional.of(friendRequest));

        // when
        friendService.updateFriendRequest(member.getId(), friendRequest.getId(), FriendStatus.N);

        // then
        assertThat(friendRequest.getFriendStatus()).isEqualTo(FriendStatus.N);
        assertThat(friendRequest.getUseStatus()).isEqualTo(UseStatus.DELETED);
    }


    @Test
    public void 친구신청을_수락_또는_거절시_이미_삭제된_친구신청일경우_예외를_던진다() {
        // given
        Friend friendRequest = new Friend(friendMember1, member, FriendStatus.IN_PROGRESS, UseStatus.DELETED);

        // when, then
        assertThatThrownBy(() -> friendService.findValidFriend(friendRequest.getId()))
                .isInstanceOf(ContentsNotFoundException.class)
                .hasMessage("존재하지 않는 내역입니다.");
    }


    @Test
    public void 친구신청을_수락_또는_거절시_로그인한_사용자에대한_친구신청이_아닐경우_예외를_던진다() {
        // given
        Friend friendRequest = new Friend(friendMember2, friendMember1, FriendStatus.IN_PROGRESS, UseStatus.IN_USE);

        given(friendRepository.findByIdAndFriendStatusAndUseStatus(friendRequest.getId(), FriendStatus.IN_PROGRESS, UseStatus.IN_USE))
                .willReturn(Optional.of(friendRequest));

        // when, then
        assertThatThrownBy(() -> friendService.updateFriendRequest(member.getId(), friendRequest.getId(), FriendStatus.Y))
                .isInstanceOf(MemberNotValidException.class)
                .hasMessage("로그인한 사용자에 대한 친구 신청이 아닙니다.");
    }

    @Test
    public void 친구를_삭제한다() {
        // given
        Friend friend = new Friend(member, friendMember1, FriendStatus.Y, UseStatus.IN_USE);

        given(friendRepository.findByIdAndUseStatus(friend.getId(), UseStatus.IN_USE))
                .willReturn(Optional.of(friend));

        // when
        friendService.deleteFriend(member.getId(), friend.getId());

        // then
        assertThat(friend.getUseStatus()).isEqualTo(UseStatus.DELETED);
    }

    @Test
    public void 요청한_친구가_로그인한_사용자의_친구가_아닐경우_예외를_던진다() {
        // given
        Friend notFriend = new Friend(member, friendMember1, FriendStatus.IN_PROGRESS, UseStatus.IN_USE);

        given(friendRepository.findByIdAndUseStatus(notFriend.getId(), UseStatus.IN_USE))
                .willReturn(Optional.of(notFriend));

        // when
        assertThatThrownBy(() -> friendService.deleteFriend(member.getId(), notFriend.getId()))
                .isInstanceOf(ContentsNotFoundException.class)
                .hasMessage("친구 상태가 아닙니다.");
    }
}
