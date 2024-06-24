package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.*;

@Transactional
public interface GoodsService {

    PageResponseDTO<GoodsDTO> getList(PageRequestDTO pageRequestDTO);

    //검색 목록
    PageResponseDTO<GoodsDTO> getSearchList(PageRequestDTO pageRequestDTO, String search);

    Long register(GoodsDTO goodsDTO);

    GoodsDTO get(Long gno);

    @Transactional
    void modifyDelFlag(Long gno, boolean delFlag);

    void modify(GoodsDTO goodsDTO);
}
