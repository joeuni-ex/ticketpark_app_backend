package org.zerock.ticketapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private String email;

    private Long gno;//굿즈 id

    private Long sno;

    private String seatClass;

    private int seatNumber;

    private int price;


}
