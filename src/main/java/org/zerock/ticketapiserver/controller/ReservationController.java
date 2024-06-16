package org.zerock.ticketapiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.zerock.ticketapiserver.dto.*;
import org.zerock.ticketapiserver.service.ReservationService;
import org.zerock.ticketapiserver.service.SeatService;


import java.security.Principal;
import java.util.Map;


@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    private final SeatService seatService;

    //조회
    @GetMapping("/{gno}")
    public ReservationDTO read(@PathVariable("rno") Long rno){

        return reservationService.get(rno);

    }


    // 목록 조회
    @GetMapping("/list")
    public PageResponseDTO<ReservationListDTO> list(PageRequestDTO pageRequestDTO, Principal principal) {

        String email = principal.getName(); // 현재 로그인 중인 유저의 정보

        return reservationService.getList(pageRequestDTO,email);

    }

    //예약 추가
    @PreAuthorize("#reservationDTO.email == authentication.name") //현재 로그인한 사용자와 dto의 email 이 동일해야 사용가능함
    @PostMapping("/")
    public Map<String,String> register(ReservationDTO reservationDTO ) {

        log.info(reservationDTO);


        Long sno = seatService.register(reservationDTO); //좌석 추가

        log.info(sno);


        reservationDTO.setSno(sno);

        reservationService.register(reservationDTO);

        return Map.of("RESULT","SUCCESS");
    }

    //수정
    @PreAuthorize("#reservationDTO.email == authentication.name") //현재 로그인한 사용자와 dto의 email 이 동일해야 사용가능함
    @PutMapping("/{rno}")
    public Map<String, String> modify(@PathVariable Long rno,  ReservationDTO reservationDTO){

        reservationDTO.setRno(rno);


        // 예약 취소
        if (reservationDTO.isCancelFlag()) {
            ReservationDTO reservation = reservationService.get(rno);
            seatService.modifyCancelFlag(reservation.getSno(), reservationDTO.isCancelFlag());
            reservationService.modifyCancelFlag(rno, reservationDTO.isCancelFlag());
        }
        // 예약 변경
        else {
            seatService.modify(reservationDTO); // 좌석 변경
            reservationService.modify(reservationDTO); // 예약일자 변경
        }

        return Map.of("RESULT","SUCCESS");

    }





}
