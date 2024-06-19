package org.zerock.ticketapiserver.repository;



import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.ticketapiserver.domain.Goods;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
@Log4j2
public class GoodsRepositoryTests {

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void testInsert(){

        for(int i=0; i<10; i++){
            Goods goods = Goods.builder()
                    .title("Test")
                    .gdesc("Test Desc")
                    .age(15)
                    .runningTime(120)
                    .place("Test Place")
                    .startDate("2024-06-13")
                    .exclusive(true)
                    .endDate("2024-06-20")
                    .genre("concert")
                    .build();


            goods.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");

            goods.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jpg");

            goods.addTimes("08:45");

            goods.addTimes("19:45");


            goodsRepository.save(goods);
        }

    }


    @Test
    public void testRead(){

        Long gno = 1L;

        //쿼리가 한 번만 날라감
        Optional<Goods> result = goodsRepository.selectOneWithImages(gno);
        Optional<Goods> result2 = goodsRepository.selectOneWithTimes(gno);



        Goods goods = result2.orElseThrow();

        log.info(goods);

//        log.info(goods.getImageList());

        log.info(goods.getTimeList());

    }

    @Commit
    @Transactional
    @Test void testDelete(){

        Long gno = 1L;

        goodsRepository.updateToDelete(gno, true);

    }

    @Test
    public void testUpdate(){

        Goods goods = goodsRepository.selectOneWithImages(1L).get();

        goods.changeTitle("Update title");

        //clearList가 아닌 ArrayList로 바꿔치기하면 안되나?
        //jpa가 arrayList로 관리하고 있으니 다른 arrayList로 변경하면 문제가 생김
        goods.clearList();

        goods.addImageString(UUID.randomUUID()+"_"+"PIMAGE1.jpg");
        goods.addImageString(UUID.randomUUID()+"_"+"PIMAGE2.jpg");
        goods.addImageString(UUID.randomUUID()+"_"+"PIMAGE3.jpg");

        goodsRepository.save(goods);
    }

    @Test
    public void testList(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        Page<Object[]> result = goodsRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    //예약 된 좌석 목록
    @Test
    @Transactional
    public void testReservedSeat() {
        Long gno = 1L;
        String time = "11:20";
        String date = "2024-07-09";
        List<String> result = goodsRepository.selectReservedSeat(gno, time,date);
        log.info("Reserved seats: {}", result);
    }

}
