package org.zerock.ticketapiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.PageRequestDTO;
import org.zerock.ticketapiserver.dto.PageResponseDTO;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class GoodsServiceTests {

    @Autowired
    private GoodsService goodsService;

    //목록 테스트
    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<GoodsDTO> responseDTO = goodsService.getList(pageRequestDTO);

        log.info(responseDTO.getDtoList());

    }

    //검색 목록 테스트
    @Test
    public void testSearchList(){
        String search = "콘";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<GoodsDTO> responseDTO = goodsService.getSearchList(pageRequestDTO,search);

        log.info(responseDTO.getDtoList());

    }


    //추가 테스트
    @Test
    public void testRegister(){

        for (int i = 0; i < 100; i++) {
            GoodsDTO goodsDTO = GoodsDTO.builder()
                    .title("새로운 콘서트"+i)
                    .gdesc("새로운 콘서트 내용"+i)
                    .place("서울 콘서트장")
                    .startDate("2024-06-05")
                    .endDate("2024-06-25")
                    .genre("concert")
                    .age(15)
                    .exclusive(false)
                    .runningTime(130)
                    .build();

            goodsDTO.setUploadFileNames(

                    java.util.List.of(
                            UUID.randomUUID()+"_"+"Test1.jpg",
                            UUID.randomUUID()+"_"+"Test2.jpg"));

            goodsDTO.setTimes(

                    java.util.List.of(
                            "09:30",
                            "15:30"));

            goodsService.register(goodsDTO);
        }
        }





}
