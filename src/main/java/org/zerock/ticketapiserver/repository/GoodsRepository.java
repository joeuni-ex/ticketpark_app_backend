package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ticketapiserver.domain.Goods;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods,Long> {

    @EntityGraph(attributePaths = "imageList")
    @Query("select g from Goods g where g.gno = :gno")
    Optional<Goods> selectOne(@Param("gno") Long gno);

}
