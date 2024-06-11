package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.Goods;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.Reservation;
import org.zerock.ticketapiserver.domain.Seat;
import org.zerock.ticketapiserver.dto.*;
import org.zerock.ticketapiserver.repository.ReservationRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {


    private final ReservationRepository reservationRepository;


    //예약 목록
    @Override
    public List<ReservationListDTO> getReservations(String email) {
        return reservationRepository.getReservationByEmail(email);
    }


    //추가
    @Override
    public void register(ReservationDTO reservationDTO) {


        // Assuming the Member and Goods are already saved in the DB
        Member member = Member.builder().email(reservationDTO.getEmail()).build();
        Goods goods = Goods.builder().gno(reservationDTO.getGno()).build();
        Seat seat = Seat.builder().sno(reservationDTO.getSno()).build();

        Reservation reservation = Reservation.builder()
                .owner(member)
                .goods(goods)
                .seat(seat)
                .reservationDate(reservationDTO.getReservationDate())
                .dueDate(reservationDTO.getDueDate())
                .build();

        log.info("------------------------------");
        log.info(reservation);

        reservationRepository.save(reservation);

    }


}
