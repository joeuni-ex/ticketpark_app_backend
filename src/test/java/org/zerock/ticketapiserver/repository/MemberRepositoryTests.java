package org.zerock.ticketapiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.MemberRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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



    //멤버 권한 변경
    @Test
    public void testUpdateMember(){

        String email = "test2@gmail..com";

        // New roles to be assigned
        List<String> newRoles = Collections.singletonList(MemberRole.ADMIN.name());


        log.info(newRoles);

        Member member = memberRepository.getWithRoles(email);


        log.info(member);

        if (member != null) {
            member.clearRole();

            List<MemberRole> roles = newRoles.stream()
                    .map(MemberRole::valueOf)
                    .collect(Collectors.toList());

            roles.forEach(member::addRole);


            log.info(member);
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

    //이메일 중복 확인
    @Test
    public void emailDuplicates(){

        String email = "user0@aaa.com";

        boolean result = memberRepository.existsByEmail(email);

        log.info( "====emailDuplicates =====");
        log.info( result );


    }
}
