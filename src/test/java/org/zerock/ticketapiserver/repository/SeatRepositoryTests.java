package org.zerock.ticketapiserver.repository;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ticketapiserver.domain.Seat;

import java.util.List;

@SpringBootTest
@Log4j2
public class SeatRepositoryTests {

    @Autowired
    private SeatRepository seatRepository;



}
