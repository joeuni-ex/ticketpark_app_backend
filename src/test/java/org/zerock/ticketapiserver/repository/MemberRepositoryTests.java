package org.zerock.ticketapiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.MemberRole;

@Log4j2
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    //추가
    @Test
    public void testInsertMember(){

        for (int i = 0; i < 10; i++) {

            Member member = Member.builder()
                    .email("user"+i+"@aaa.com")
                    .pw( passwordEncoder.encode("1111"))
                    .nickname("USER"+i)
                    .build();

            member.addRole(MemberRole.USER);


            if(i>=8){

                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);

        }

    }

    //조회
    @Test
    public void testRead(){

        String email = "user0@aaa.com";

        Member member = memberRepository.getWithRoles(email);

        log.info("==============");
        log.info(member);
        log.info(member.getMemberRoleList());


    }
}
