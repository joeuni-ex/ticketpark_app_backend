package org.zerock.ticketapiserver.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"owner", "review"})
@Table(name = "tbl_like", indexes = { //인덱스 생성 -> 조회 편리
        @Index(columnList = "member_owner", name = "idx_member_email_like"),
        @Index(columnList = "review_reno", name = "idx_review_reno_like"),
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    @ManyToOne
    @JoinColumn(name = "member_owner")//컬럼명을 사용하는 이유는 인덱스를 사용하기 위해서
    private Member owner;// 예약 회원

    @OneToOne
    @JoinColumn(name = "review_reno")
    private Review review;
}
