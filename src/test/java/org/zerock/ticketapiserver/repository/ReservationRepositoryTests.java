package org.zerock.ticketapiserver.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.ticketapiserver.domain.Goods;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.Reservation;
import org.zerock.ticketapiserver.domain.Seat;
import org.zerock.ticketapiserver.dto.ReservationDTO;

@SpringBootTest
@Log4j2
public class ReservationRepositoryTests {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;



    @Test
    public void testListOfMember(){

        String email = "hong@gmail.com";

        reservationRepository.getReservationByEmail(email);


    }

    @Transactional
    @Commit //테스트가 실제 db에 반영하기 위해서
    @Test
    public void testInsertReservation(){

        String email = "hong@gmail.com";
        Long gno = 1L;
        String seatClass = "VIP";
        int price = 130000;
        Long sno = 2L;

        // Assuming the Member and Goods are already saved in the DB
        Member member = Member.builder().email(email).build();
        Goods goods = Goods.builder().gno(gno).build();
        Seat seat = Seat.builder().sno(sno).build();

        seatRepository.save(seat);

        Reservation reservation = Reservation.builder()
                .owner(member)
                .goods(goods)
                .seat(seat)
                .build();

        reservationRepository.save(reservation);
    }


    @Test
    @Commit //테스트가 실제 db에 반영하기 위해서
    public void testInsertSeat(){
        String seatClass = "VIP";
        int price = 130000;
        Long gno = 1L;
        int seatNumber = 1;

        Goods goods = Goods.builder().gno(gno).build();
        Seat seat = Seat.builder().seatClass(seatClass).price(price).seatNumber(seatNumber).goods(goods).build();


        log.info("=======================");
        log.info(seatRepository.save(seat));

    }

}
