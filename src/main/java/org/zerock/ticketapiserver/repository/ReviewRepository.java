package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Like;
import org.zerock.ticketapiserver.domain.Review;
import org.zerock.ticketapiserver.dto.CheckWrittenReviewDTO;
import org.zerock.ticketapiserver.dto.ReviewDTO;
import org.zerock.ticketapiserver.dto.ReviewListDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    //deleteFlage -> true로 변경하여 삭제처리
    @Modifying
    @Query("update Review re set re.deleteFlag = :deleteFlag where re.reno = :reno")
    void updateToDelete(@Param("reno") Long reno, @Param("deleteFlag") boolean deleteFlag);

    @Query("select re from Review re " +
            "join re.goods g " +
            "join g.imageList gi " +
            "where gi.ord = 0 " +
            "and re.goods.gno = :gno " +
            "and re.deleteFlag = false " +
            "order by re.reno desc")
    List<Review> findByGoodsGno(@Param("gno") Long gno);

    //유저 별 리뷰 정보
    @Query("select " +
            "new org.zerock.ticketapiserver.dto.ReviewListDTO(re.reno, re.content , re.owner.nickname, re.likes,re.grade ,re.reservation.reservationDate ,gi.fileName, g.gno, g.title, re.createDate, re.deleteFlag) " +
            "from Review re " +
            "join re.goods g " +
            "join g.imageList gi " +
            "where gi.ord = 0 " +
            "and re.owner.email = :email " +
            "and re.deleteFlag = false " +
            "order by re.reno desc")
    List<ReviewListDTO> getReviewsOfReviewDtoByEmail(@Param("email") String email); //email을 가지고 리뷰 목록 가져오기


    //굿즈 별 리뷰 정보
    @Query("select " +
            "new org.zerock.ticketapiserver.dto.ReviewListDTO(re.reno, re.content ,re.owner.nickname , re.likes,re.grade ,re.reservation.reservationDate ,gi.fileName, g.gno, g.title, re.createDate, re.deleteFlag) " +
            "from Review re " +
            "join re.goods g " +
            "join g.imageList gi " +
            "where gi.ord = 0 " +
            "and re.goods.gno = :gno " +
            "and re.deleteFlag = false " +
            "order by re.reno desc")
    List<ReviewListDTO> getReviewsOfReviewDtoByGno(@Param("gno") Long gno); //gno를 가지고 리뷰 목록 가져오기


    //특정 예약에 리뷰 작성했는지 여부 체크
    @Query("select re from Review re where re.reservation.rno = :rno")
    Optional<Review> findByReservation(@Param("rno") Long rno);


}
