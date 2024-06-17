package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.*;

@Transactional
public interface GoodsService {

    PageResponseDTO<GoodsDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(GoodsDTO goodsDTO);

    //예약 된 좌석 조회
    ReservedSeatResponseDTO selectReservedSeat(ReservedSeatRequestDTO reservedSeatRequestDTO);

    GoodsDTO get(Long gno);

    @Transactional
    void modifyDelFlag(Long gno, boolean delFlag);

    void modify(GoodsDTO goodsDTO);
}
