//package com.mmd.oauth.application;
//
//import com.mmd.domain.MemberRole;
//import com.mmd.domain.OAuthProvider;
//import com.mmd.entity.Member;
//import com.mmd.oauth.application.dto.OAuth2Member;
//import com.mmd.oauth.application.dto.OAuthAttributes;
//import com.mmd.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class OAuth2ServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    private final MemberRepository memberRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        log.info("OAuth2MemberService.loadUser start");
//
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest); // 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서 사용자 정보를 받아온다.
//
//        // OAuth2 서비스 RegistrationId 추출(kakao/naver/google)
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//
//
//        // OAuth2 key 추출
//        String userNameAttributeName = userRequest.getClientRegistration()
//                .getProviderDetails()
//                .getUserInfoEndpoint()
//                .getUserNameAttributeName();
//
//        // 소셜에서 API가 제공하는 유저 정보의 JSON 가져오기
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // 소셜 구분에 따라 OAuthAttributes 생성
//        OAuthAttributes extractAttributes = OAuthAttributes.of(OAuthProvider, userNameAttributeName, attributes);
//
//        Member createdMember = getMember(extractAttributes, OAuthProvider);
//
//        // DefaultOAuth2User 구현체 반환
//        return new CustomOAuth2Member(
//                Collections.singleton(new SimpleGrantedAuthority(createdMember.getRole().toString())),
//                attributes,
//                extractAttributes.getNameAttributeKey(),
//                createdMember.getEmail(),
//                createdMember.getRole()
//        );
//
//    }
//
//    private Member getMember(OAuthAttributes attributes, OAuthProvider OAuthProvider) {
//        // 이미 존재하는 경우 가져오고, 없으면 데이터를 업데이트한다.
//        return memberRepository.findBySocialTypeAndSocialId(OAuthProvider, attributes.getOAuth2Member().getSocialId())
//                .orElse(saveMember(attributes, OAuthProvider));
//    }
//
//    private Member saveMember(OAuthAttributes attributes, OAuthProvider OAuthProvider) {
//        OAuth2Member oAuth2Member = attributes.getOAuth2Member();
//        Member createdMember = Member.builder()
//                .socialType(OAuthProvider)
//                .socialId(oAuth2Member.getSocialId())
//                .email(oAuth2Member.getEmail())
//                .name(oAuth2Member.getName())
//                .nickName(oAuth2Member.getNickname())
//                .role(MemberRole.GUEST)
//                .build();
//
//        return memberRepository.save(createdMember);
//    }
//
//    private OAuthProvider getSocialType(String registrationId) {
//        if (NAVER_REGISTRATION_ID.equals(registrationId)) {
//            return OAuthProvider.NAVER;
//        }
//        if (KAKAO_REGISTRATION_ID.equals(registrationId)) {
//            return OAuthProvider.KAKAO;
//        }
//        return OAuthProvider.GOOGLE;
//    }
//
//}
