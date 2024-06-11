package org.zerock.ticketapiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.ReservationDTO;
import org.zerock.ticketapiserver.service.ReservationService;
import org.zerock.ticketapiserver.service.SeatService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    private final SeatService seatService;

    @PreAuthorize("#reservationDTO.email == authentication.name") //현재 로그인한 사용자와 dto의 email 이 동일해야 사용가능함
    @PostMapping("/")
    public Map<String,String> register(ReservationDTO reservationDTO ) {

        Long sno = seatService.register(reservationDTO); //좌석 추가

        reservationDTO.setSno(sno);

        log.info(reservationDTO) ;

        log.info(sno);

        reservationService.register(reservationDTO);

        return Map.of("RESULT","SUCCESS");
    }


}
