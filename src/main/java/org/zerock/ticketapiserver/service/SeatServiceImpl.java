package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.Goods;
import org.zerock.ticketapiserver.domain.Seat;
import org.zerock.ticketapiserver.dto.ReservationDTO;
import org.zerock.ticketapiserver.repository.SeatRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    //추가
    @Override
    public Long register(ReservationDTO reservationDTO) {
        Goods goods = Goods.builder().gno(reservationDTO.getGno()).build();

        Seat seat = Seat.builder()
                .goods(goods)
                .seatClass(reservationDTO.getSeatClass())
                .seatNumber(reservationDTO.getSeatNumber())
                .price(reservationDTO.getPrice())
                .build();

        log.info("------------------------------");
        log.info(seat);


        Long sno = seatRepository.save(seat).getSno();

        return sno;
    }

}
