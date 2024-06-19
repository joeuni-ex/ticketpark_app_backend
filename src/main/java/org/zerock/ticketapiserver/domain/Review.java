package org.zerock.ticketapiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"owner","goods"})
@Table(name = "tbl_review", indexes = { //인덱스 생성 -> 조회 편리
        @Index(columnList = "member_owner", name = "idx_member_email_review"),
        @Index(columnList = "goods_gno", name = "idx_goodsitem_gno_review"),
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reno;

    private String content; //예약일자
    
    private int likes; //좋아요

    @ManyToOne
    @JoinColumn(name = "member_owner")//컬럼명을 사용하는 이유는 인덱스를 사용하기 위해서
    private Member owner;// 예약 회원

    @ManyToOne
    @JoinColumn(name = "goods_gno")
    private Goods goods;
    
    @Column(nullable = false, updatable = false)
    private LocalDate createDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDate.now();
    }

    //likes +1
    public void changeIncreaseLikes
            (int likes){
        this.likes =likes+1;
    }

}
