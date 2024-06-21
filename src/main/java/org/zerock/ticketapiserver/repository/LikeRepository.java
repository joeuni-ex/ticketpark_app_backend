package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Like;

import java.util.Optional;


public interface LikeRepository extends CrudRepository<Like, Long> {

    //이미 해당 리뷰에 좋아요를 추가했는지 여부 체크
    @Query("select l from Like l where l.review.reno = :reno and l.owner.email = :email")
    Optional<Like> findByReviewAndOwner(@Param("reno") Long reno, @Param("email") String email);
}
