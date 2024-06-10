package org.zerock.ticketapiserver.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"owner","goods","seat"})
@Table(name = "tbl_reservation", indexes = { //인덱스 생성 -> 조회 편리
        @Index(columnList = "member_owner", name = "idx_member_email_reservation"),
        @Index(columnList = "goods_gno", name = "idx_goodsitem_pno_reservation"),
        @Index(columnList = "seat_sno", name = "idx_seatitem_sno_reservation")
})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne
    @JoinColumn(name = "member_owner")//컬럼명을 사용하는 이유는 인덱스를 사용하기 위해서
    private Member owner;// 예약 회원

    @ManyToOne
    @JoinColumn(name = "goods_gno")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "seat_sno")
    private Seat seat;


}
