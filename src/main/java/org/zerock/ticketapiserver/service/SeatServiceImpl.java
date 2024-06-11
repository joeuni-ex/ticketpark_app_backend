package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.Goods;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.Reservation;
import org.zerock.ticketapiserver.domain.Seat;
import org.zerock.ticketapiserver.dto.ReservationDTO;
import org.zerock.ticketapiserver.repository.SeatRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    //추가
    @Override
    public Long register(ReservationDTO reservationDTO) {
        Seat seat = dtoToEntity(reservationDTO);

        Long sno = seatRepository.save(seat).getSno();

        return sno;
    }


    //좌석변경
    @Override
    public Long modify(ReservationDTO reservationDTO) {

        //조회
        Optional<Seat> result = seatRepository.findById(reservationDTO.getSno());

        Seat seat = result.orElseThrow();
        //변경 내용 반영
        seat.changeSeatClass(reservationDTO.getSeatClass());
        seat.changeSeatNumber(reservationDTO.getSeatNumber());
        seat.changeSeatPrice(reservationDTO.getPrice());

        Long sno = seatRepository.save(seat).getSno();

        return sno;
    }





    // DTO -> 엔티티 변환 (저장 시 사용)
    private Seat dtoToEntity(ReservationDTO seatDTO) {
        Goods goods = Goods.builder().gno(seatDTO.getGno()).build();

        Seat seat = Seat.builder()
                .goods(goods)
                .seatClass(seatDTO.getSeatClass())
                .seatNumber(seatDTO.getSeatNumber())
                .price(seatDTO.getPrice())
                .build();

        return seat;
    }

    // 엔티티 -> DTO 변환 (조회 시 사용)
    private ReservationDTO entityToDto(Seat seat) {
        ReservationDTO reservationDTO = ReservationDTO.builder()
                .sno(seat.getSno())
                .gno(seat.getGoods().getGno())
                .seatClass(seat.getSeatClass())
                .seatNumber(seat.getSeatNumber())
                .price(seat.getPrice())
                .build();

        return reservationDTO;
    }
}
