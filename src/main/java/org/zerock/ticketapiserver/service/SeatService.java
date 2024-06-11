package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.ReservationDTO;

@Transactional
public interface SeatService {


    //추가
    Long register(ReservationDTO registrationDTO);

    //좌석변경
    Long modify(ReservationDTO reservationDTO);
}
