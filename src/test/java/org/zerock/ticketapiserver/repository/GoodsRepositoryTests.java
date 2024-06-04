package org.zerock.ticketapiserver.repository;



import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ticketapiserver.domain.Goods;

import java.util.Optional;
import java.util.UUID;


@SpringBootTest
@Log4j2
public class GoodsRepositoryTests {

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void testInsert(){

        Goods goods = Goods.builder()
                .title("Test")
                .gdesc("Test Desc")
                .age(15)
                .time(120)
                .place("Test Place")
                .genre("concert")
                .build();


        goods.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");

        goods.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jpg");


        goodsRepository.save(goods);
    }


    @Test
    public void testRead(){

        Long gno = 1L;

        //ProductRepository 에서 생성한 selectOne
        //쿼리가 한 번만 날라감
        Optional<Goods> result = goodsRepository.selectOne(gno);

        Goods goods = result.orElseThrow();

        log.info(goods);

        log.info(goods.getImageList());

    }

}
