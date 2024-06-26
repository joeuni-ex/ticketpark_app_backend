package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.MemberRole;
import org.zerock.ticketapiserver.dto.CheckEmailDTO;
import org.zerock.ticketapiserver.dto.MemberDTO;
import org.zerock.ticketapiserver.repository.MemberRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    //추가
    @Override
    public void register(MemberDTO memberDTO) {

        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .pw( passwordEncoder.encode(memberDTO.getPw()))
                .nickname(memberDTO.getNickname())
                .social(memberDTO.isSocial())
                .build();

        List<MemberRole> roles = memberDTO.getRoleNames().stream()
                .map(MemberRole::valueOf)
                .collect(Collectors.toList());

        roles.forEach(member::addRole);

        log.info(member);
        memberRepository.save(member);


    }

    // 멤버 권한 변경
    @Override
    public void updateMemberRoles(String email, List<String> newRoles) {
        Member member = memberRepository.getWithRoles(email);

        if (member != null) {
            member.clearRole();

            List<MemberRole> roles = newRoles.stream()
                    .map(MemberRole::valueOf)
                    .collect(Collectors.toList());

            roles.forEach(member::addRole);

            log.info("Updated member roles: {}", member);
            memberRepository.save(member);
        } else {
            log.warn("Member not found with email: {}", email);
        }
    }


    //이메일 중복 확인
    @Override
    public boolean emailDuplicates(CheckEmailDTO checkEmailDTO) {

      return memberRepository.existsByEmail(checkEmailDTO.getEmail());

    }


    @Override
    public MemberDTO getKakaoMember(String accessToken) {
        //1.accessToken을 이용해서 사용자의 정보를 가져온다.
        //카카오 연동 닉네임 -- 이메일 주소에 해당
        String nickname = getEmailFromKakaoAccessToken(accessToken);


        //2.기존에 DB에 회원 정보가 있는 경우 OR 없는 경우 처리
        Optional<Member> result = memberRepository.findByEmail(nickname);


        if(result.isPresent()){

            //return

        }
        //DB에 카카오 인증 유저가 없음
        Member socialMember = makeSocailMember(nickname);

        memberRepository.save(socialMember);

        MemberDTO memberDTO = entityToDTO(socialMember);


        return memberDTO;
    }

    private String getEmailFromKakaoAccessToken(String accessToken){

        //카카오 호출 URL
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        //Header 지정
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer "+accessToken);
        headers.add("Content-type","pplication/x-www-form-urlencoded;charset=utf-8");


        HttpEntity<String > entity = new HttpEntity<>(headers);

        //실제로 보냄
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        //데이터 호출-> LinkedHashMap으로 리턴됨
        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriBuilder.toUri(), HttpMethod.GET,entity, LinkedHashMap.class);

        //결과 확인
        log.info("response..............");
        log.info(response);

        LinkedHashMap<String,LinkedHashMap> bodyMap = response.getBody();

        LinkedHashMap<String, String> kakkaoAcount = bodyMap.get("properties");

        log.info("kakaoAcount: " + kakkaoAcount);

        String nickname = kakkaoAcount.get("nickname");

        log.info("nickname: "+ nickname);

        return nickname;
    }

    //db에 유저 없을 경우 생성
    private Member makeSocailMember(String email){

        String tempPassword = makeTempPassword();

        log.info("tempPassword: " +tempPassword);

        Member member = Member.builder()
                .email(email)
                .pw(passwordEncoder.encode(tempPassword))
                .nickname("Social Member")
                .social(true) //소셜 로그인 -> 사용자 정보 수정
                .build();
        member.addRole(MemberRole.USER);

        return member;
    }

    // 카카오 연동 시 pw 10자리 랜덤 생성
    private String makeTempPassword(){

        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i<10; i++){
            buffer.append((char) ((int)(Math.random()*55) +65));
        }
        return buffer.toString();
    }
}
