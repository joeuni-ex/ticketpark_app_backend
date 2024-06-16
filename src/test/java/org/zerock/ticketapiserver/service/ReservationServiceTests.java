package org.zerock.ticketapiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ticketapiserver.dto.*;

@Log4j2
@SpringBootTest
public class ReservationServiceTests {

    @Autowired
    private ReservationService reservationService;

    //목록 테스트
    @Test
    public void testRead(){

        Long rno = 1L;

        ReservationDTO result = reservationService.get(rno);

        log.info(result);

    }


    //목록 테스트
    @Test
    public void testList(){

        String email = "user7@gmail.com";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<ReservationListDTO> responseDTO = reservationService.getList(pageRequestDTO,email);

        log.info(responseDTO.getDtoList());

    }

}
