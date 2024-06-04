package org.zerock.ticketapiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.PageRequestDTO;
import org.zerock.ticketapiserver.dto.PageResponseDTO;

@SpringBootTest
@Log4j2
public class GoodsServiceTests {

    @Autowired
    private GoodsService goodsService;

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<GoodsDTO> responseDTO = goodsService.getList(pageRequestDTO);

        log.info(responseDTO.getDtoList());

    }
}
