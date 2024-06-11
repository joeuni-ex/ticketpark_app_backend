package org.zerock.ticketapiserver.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.time.LocalDate;

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

    private String reservationDate; //예약일자

    @ManyToOne
    @JoinColumn(name = "member_owner")//컬럼명을 사용하는 이유는 인덱스를 사용하기 위해서
    private Member owner;// 예약 회원

    @ManyToOne
    @JoinColumn(name = "goods_gno")
    private Goods goods;

    @OneToOne
    @JoinColumn(name = "seat_sno")
    private Seat seat;


    @Column(nullable = false, updatable = false)
    private LocalDate dueDate;

    private boolean cancelFlag = false; //예약 취소 여부


    @PrePersist
    protected void onCreate() {
        this.dueDate = LocalDate.now();
    }

    //예약일자 변경
    public void changeReservationDate (String reservationDate){
        this.reservationDate =reservationDate;
    }






}
