package org.zerock.ticketapiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString(exclude = {"goods"})
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_seat", indexes = { //인덱스 생성 -> 조회 편리
        @Index(columnList = "goods_gno", name = "idx_goodsitem_gno_seat")
})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    private String seatClass;

    private int seatNumber;

    private int price;

    @ManyToOne
    @JoinColumn(name = "goods_gno")
    private Goods goods;


    //좌석클래스 변경
    public void changeSeatClass (String seatClass){
        this.seatClass =seatClass;
    }


    //좌석번호 변경
    public void changeSeatNumber (int seatNumber){
        this.seatNumber =seatNumber;
    }



    //좌석가격 변경
    public void changeSeatPrice (int price){
        this.price =price;
    }



}
