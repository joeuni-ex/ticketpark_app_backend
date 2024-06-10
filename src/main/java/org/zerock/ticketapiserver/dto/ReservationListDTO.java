package org.zerock.ticketapiserver.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.ticketapiserver.domain.Reservation;

@Data
@Builder
@NoArgsConstructor
public class ReservationListDTO {


    private Long rno;

    private String gtitle;//굿즈 이름

    private String seatClass ; //좌석 클래스

    private int seatNumber;//좌석 번호

    private int price;//가격

    private String imageFile; //이미지

    public ReservationListDTO(Long rno, String gtitle,  String seatClass, int seatNumber , int price , String imageFile) {
        this.rno = rno;
        this.gtitle = gtitle;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.price = price;
        this.imageFile = imageFile;
        
    }
}
