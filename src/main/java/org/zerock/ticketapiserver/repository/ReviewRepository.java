package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    //deleteFlage -> true로 변경하여 삭제처리
    @Modifying
    @Query("update Review re set re.deleteFlag = :deleteFlag where re.reno = :reno")
    void updateToDelete(@Param("reno") Long reno, @Param("deleteFlag") boolean deleteFlag);

}
