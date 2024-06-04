package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.PageRequestDTO;
import org.zerock.ticketapiserver.dto.PageResponseDTO;

@Transactional
public interface GoodsService {

    PageResponseDTO<GoodsDTO> getList(PageRequestDTO pageRequestDTO);

}
