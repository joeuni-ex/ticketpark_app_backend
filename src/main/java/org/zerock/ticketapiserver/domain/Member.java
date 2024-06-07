package org.zerock.ticketapiserver.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memberRoleList")//연관관계 제외
public class Member {

    @Id
    private String email;

    private String pw;

    private String nickname;

    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    //권한 추가
    public void addRole(MemberRole memberRole){

        memberRoleList.add(memberRole);

    }

    //권한 삭제
    public void clearRole(){

        memberRoleList.clear();

    }


    //닉네임 변경
    public void changeNickname(String nickname){

        this.nickname = nickname;

    }

    //패스워드 변경
    public void changePw(String pw){

        this.pw=pw;

    }

    //소셜 변경
    public void changeSocial(boolean social){

        this.social = social;

    }
}
