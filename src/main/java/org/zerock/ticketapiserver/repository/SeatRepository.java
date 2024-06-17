package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Goods;
import org.zerock.ticketapiserver.domain.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    //cancelFlag -> true로 변경하여 삭제처리
    @Modifying
    @Query("update Seat s set s.cancelFlag = :cancelFlag where s.sno = :sno")
    void updateToCancel(@Param("sno") Long sno, @Param("cancelFlag") boolean cancelFlag);

//    @Query("select s.seatNumber " +
//            "from Seat s " +
//            "join Goods g " +
//            "join GoodsTime g " +
//            "where s.goods.gno = :gno and " +
//            "s.cancelFlag = false and " +
//            "g.timeList.time = :time")
//    List<String> selectReservedSeat(@Param("gno") Long gno, @Param("time") String time);


}
