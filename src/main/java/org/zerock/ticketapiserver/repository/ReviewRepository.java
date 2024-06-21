package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Review;
import org.zerock.ticketapiserver.dto.ReviewDTO;
import org.zerock.ticketapiserver.dto.ReviewListDTO;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    //deleteFlage -> true로 변경하여 삭제처리
    @Modifying
    @Query("update Review re set re.deleteFlag = :deleteFlag where re.reno = :reno")
    void updateToDelete(@Param("reno") Long reno, @Param("deleteFlag") boolean deleteFlag);


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


    //유저 별 리뷰 정보
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
}
