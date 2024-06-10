package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.CheckEmailDTO;
import org.zerock.ticketapiserver.dto.MemberDTO;

@Transactional
public interface MemberService {
    //추가
    void register(MemberDTO memberDTO);


    //이메일 중복 확인
    boolean emailDuplicates(CheckEmailDTO checkEmailDTO);
}
