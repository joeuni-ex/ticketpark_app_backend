package org.zerock.ticketapiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReservationRepositoryTests {

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    public void testListOfMember(){

        String email = "hong@gmail.com";

        reservationRepository.getReservationByEmail(email);



    }

}
