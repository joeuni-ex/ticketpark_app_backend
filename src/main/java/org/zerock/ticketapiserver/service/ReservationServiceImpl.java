package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.*;
import org.zerock.ticketapiserver.dto.*;
import org.zerock.ticketapiserver.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {


    private final ReservationRepository reservationRepository;

    //예약 조회
    @Override
    public ReservationDTO get(Long rno) {

        Optional<Reservation> result = reservationRepository.findById(rno);

        Reservation reservation = result.orElseThrow();

        return entityToDto(reservation);
    }


    //예약 목록
    @Override
    public List<ReservationListDTO> getReservations(String email) {
        return reservationRepository.getReservationByEmail(email);
    }


    //추가
    @Override
    public void register(ReservationDTO reservationDTO) {

        reservationRepository.save(dtoToEntity(reservationDTO));

    }


    // 수정
    @Override
    public void modify(ReservationDTO reservationDTO) {
        Optional<Reservation> result = reservationRepository.findById(reservationDTO.getRno());

        if (result.isPresent()) {
            Reservation reservation = result.get();

            reservation.changeReservationDate(reservationDTO.getReservationDate());

            reservationRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("Reservation not found for ID: " + reservationDTO.getRno());
        }
    }

    //예약 취소
    @Override
    @Transactional
    public void modifyCancelFlag(Long rno, boolean cancelFlag) {
        reservationRepository.updateToCancel(rno, cancelFlag);
    }



    // DTO -> 엔티티 변환 (저장 시 사용)
    private Reservation dtoToEntity(ReservationDTO reservationDTO) {

        Member member = Member.builder().email(reservationDTO.getEmail()).build();
        Goods goods = Goods.builder().gno(reservationDTO.getGno()).build();
        Seat seat = Seat.builder().sno(reservationDTO.getSno()).build();

        Reservation reservation = Reservation.builder()
                .owner(member)
                .goods(goods)
                .seat(seat)
                .reservationDate(reservationDTO.getReservationDate())
                .dueDate(reservationDTO.getDueDate())
                .cancelFlag(reservationDTO.isCancelFlag())
                .build();

        return reservation;
    }

    //entitiy->dto 변환 (조회 시 사용)
    private ReservationDTO entityToDto(Reservation reservation){


        ReservationDTO reservationDTO = ReservationDTO.builder()
                .rno(reservation.getRno())
                .email(reservation.getOwner().getEmail())
                .gno(reservation.getGoods().getGno())
                .sno(reservation.getSeat().getSno())
                .reservationDate(reservation.getReservationDate())
                .seatClass(reservation.getSeat().getSeatClass())
                .seatNumber(reservation.getSeat().getSeatNumber())
                .price(reservation.getSeat().getPrice())
                .dueDate(reservation.getDueDate())
                .cancelFlag(reservation.isCancelFlag())
                .build();


        return reservationDTO;
    }



}
