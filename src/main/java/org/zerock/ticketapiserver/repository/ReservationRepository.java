package org.zerock.ticketapiserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Reservation;
import org.zerock.ticketapiserver.dto.ReservationListDTO;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select " +
            "new org.zerock.ticketapiserver.dto.ReservationListDTO(re.rno, g.gno, g.title, g.place, re.reservationDate, s.seatClass, s.seatNumber, re.time, s.price, gi.fileName, re.dueDate, re.cancelFlag) " +
            "from Reservation re " +
            "join re.goods g " +
            "join re.seat s " +
            "left join g.imageList gi " +
            "where gi.ord = 0 " +
            "and re.owner.email = :email " +
            "and re.cancelFlag = false " +
            "order by re.rno desc")
     Page<ReservationListDTO> getReservationByEmail( Pageable pageable,@Param("email") String email); //email을 가지고 예약목록 가져오기


    //cancelFlag -> true로 변경하여 삭제처리
    @Modifying
    @Query("update Reservation r set r.cancelFlag = :cancelFlag where r.rno = :rno")
    void updateToCancel(@Param("rno") Long rno, @Param("cancelFlag") boolean cancelFlag);

    //현재 예약된 좌석 조회
    @Query("select distinct s.seatNumber from Reservation r " +
            "join r.seat s " +
            "where r.goods.gno = :gno and s.cancelFlag = false and r.time =:time and r.reservationDate = :date ")
    List<String> selectReservedSeat(@Param("gno") Long gno, @Param("time") String time, @Param("date") String date);



}
