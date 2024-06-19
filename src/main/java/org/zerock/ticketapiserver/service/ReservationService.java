package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.*;

import java.util.List;

@Transactional
public interface ReservationService {
    //예약 조회
    ReservationDTO get(Long sno);


    PageResponseDTO<ReservationListDTO> getList(PageRequestDTO pageRequestDTO, String email);

    //추가
    void register(ReservationDTO reservationDTO);

    // 수정
    void modify(ReservationDTO reservationDTO);

    //예약 취소
    void modifyCancelFlag(Long rno, boolean cancelFlag);

    //예약 된 좌석 조회
    ReservedSeatResponseDTO selectReservedSeat(ReservedSeatRequestDTO reservedSeatRequestDTO);
}
