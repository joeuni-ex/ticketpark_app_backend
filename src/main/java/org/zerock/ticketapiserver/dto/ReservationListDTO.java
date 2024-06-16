package org.zerock.ticketapiserver.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.ticketapiserver.domain.Reservation;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class ReservationListDTO {


    private Long rno;

    private Long gno;

    private String gtitle;//굿즈 이름
    
    private String place;//장소

    private String reservationDate; //예약일자

    private String seatClass ; //좌석 클래스

    private int seatNumber;//좌석 번호

    private String time;

    private int price;//가격

    private String imageFile; //이미지

    private LocalDate dueDate;

    private boolean cancelFlag;

    public ReservationListDTO(Long rno, Long gno, String gtitle, String place, String reservationDate, String seatClass, int seatNumber , String time, int price , String imageFile , LocalDate dueDate, boolean cancelFlag) {
        this.rno = rno;
        this.gno = gno;
        this.gtitle = gtitle;
        this.place = place;
        this.reservationDate = reservationDate;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.time = time;
        this.price = price;
        this.imageFile = imageFile;
        this.dueDate = dueDate;
        this.cancelFlag = cancelFlag;
        
    }
}
