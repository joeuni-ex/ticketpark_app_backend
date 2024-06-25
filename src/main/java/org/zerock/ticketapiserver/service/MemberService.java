package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.dto.CheckEmailDTO;
import org.zerock.ticketapiserver.dto.MemberDTO;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {
    //추가
    void register(MemberDTO memberDTO);


    //이메일 중복 확인
    boolean emailDuplicates(CheckEmailDTO checkEmailDTO);

    MemberDTO getKakaoMember(String accessToken);



    default MemberDTO entityToDTO(Member member){

        MemberDTO dto = new MemberDTO(

                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));

        return dto;

    }
}
