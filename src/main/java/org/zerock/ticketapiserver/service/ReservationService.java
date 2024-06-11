package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.ReservationDTO;
import org.zerock.ticketapiserver.dto.ReservationListDTO;

import java.util.List;

@Transactional
public interface ReservationService {
    //예약 조회
    ReservationDTO get(Long sno);

    List<ReservationListDTO> getReservations(String email);

    //추가
    void register(ReservationDTO reservationDTO);

    // 수정
    void modify(ReservationDTO reservationDTO);

    //예약 취소
    void modifyCancelFlag(Long rno, boolean cancelFlag);
}
