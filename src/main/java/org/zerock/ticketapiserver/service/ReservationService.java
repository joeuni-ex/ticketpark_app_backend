package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.ReservationDTO;
import org.zerock.ticketapiserver.dto.ReservationListDTO;

import java.util.List;

@Transactional
public interface ReservationService {
    List<ReservationListDTO> getReservations(String email);

    //추가
    void register(ReservationDTO reservationDTO);
}
