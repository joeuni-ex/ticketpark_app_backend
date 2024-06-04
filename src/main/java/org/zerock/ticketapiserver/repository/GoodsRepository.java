package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Goods;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods,Long> {
    //조회 시 이미지 리스트 조인하여 함께 조회
    @EntityGraph(attributePaths = "imageList")
    @Query("select g from Goods g where g.gno = :gno")
    Optional<Goods> selectOne(@Param("gno") Long gno);

    //delFlag -> true로 변경하여 삭제처리
    @Modifying
    @Query("update Goods g set g.delFlag = :delFlag where g.gno = :gno")
    void updateToDelete(@Param("gno") Long gno, @Param("delFlag") boolean flag);

}
