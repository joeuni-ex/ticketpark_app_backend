package org.zerock.ticketapiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.MemberRole;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Log4j2
public class MemberServiceTests {


    @Autowired
    private MemberService memberService;


}
