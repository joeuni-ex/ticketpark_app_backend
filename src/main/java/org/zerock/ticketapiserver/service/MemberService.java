package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.MemberDTO;

@Transactional
public interface MemberService {
    //추가
    void register(MemberDTO memberDTO);
}
