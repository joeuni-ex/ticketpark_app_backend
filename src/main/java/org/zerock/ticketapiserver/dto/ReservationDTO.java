package org.zerock.ticketapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long rno;

    private String email;

    private Long gno;//굿즈 id

    private Long sno;

    private String reservationDate; //예약일자

    private String seatClass;

    private int seatNumber;

    private int price;

    private LocalDate dueDate;


}
