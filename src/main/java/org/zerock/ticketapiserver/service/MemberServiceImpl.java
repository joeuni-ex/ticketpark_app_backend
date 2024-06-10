package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.MemberRole;
import org.zerock.ticketapiserver.dto.CheckEmailDTO;
import org.zerock.ticketapiserver.dto.MemberDTO;
import org.zerock.ticketapiserver.repository.MemberRepository;

import java.util.List;
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

    //이메일 중복 확인
    @Override
    public boolean emailDuplicates(CheckEmailDTO checkEmailDTO) {

      return memberRepository.existsByEmail(checkEmailDTO.getEmail());

    }
}
