package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.Goods;
import org.zerock.ticketapiserver.domain.GoodsImage;
import org.zerock.ticketapiserver.domain.GoodsTime;
import org.zerock.ticketapiserver.dto.*;
import org.zerock.ticketapiserver.repository.GoodsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    @Override
    public PageResponseDTO<GoodsDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, // 0부터 시작하니가 -1
                pageRequestDTO.getSize(),
                Sort.by("gno").descending());

        Page<Object[]> result;

        log.info(pageRequestDTO);

        if (pageRequestDTO.getGenre().equals("all")) {
            result = goodsRepository.selectList(pageable);
        } else {
            result = goodsRepository.selectListOfGenre(pageable, pageRequestDTO.getGenre());
        }

        //Object[] = 0.product 1.productImage
        //Object[] = 0.product 1.productImage
        //Object[] = 0.product 1.productImage 과 같이 배열로 되어있음

        //map을 이용해서 DTO로 변환하기
        List<GoodsDTO> dtoList = result.get().map(arr ->{

            GoodsDTO goodsDTO =null;


            Goods goods = (Goods) arr[0];
            GoodsImage goodsImage = (GoodsImage) arr[1];

            goodsDTO = GoodsDTO.builder()
                    . gno(goods.getGno())
                    .title(goods.getTitle())
                    .gdesc(goods.getGdesc())
                    .place(goods.getPlace())
                    .startDate(goods.getStartDate())
                    .endDate(goods.getEndDate())
                    .age(goods.getAge())
                    .runningTime(goods.getRunningTime())
                    .genre(goods.getGenre())
                    .exclusive(goods.isExclusive())
                    .build();

            String imageStr = goodsImage.getFileName();

            goodsDTO.setUploadFileNames(List.of(imageStr));


            return goodsDTO;
        }).collect(Collectors.toList()); //collect(Collectors.toList()) 리스트로 바꿈

        long totalCount = result.getTotalElements();


        return PageResponseDTO.<GoodsDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    //추가
    @Override
    public Long register(GoodsDTO goodsDTO) {
        Goods goods = dtoToEntity(goodsDTO);

        Long gno = goodsRepository.save(goods).getGno();

        return gno;
    }

    //조회
    @Override
    public GoodsDTO get(Long gno) {

        //이미지
        Optional<Goods> result = goodsRepository.selectOneWithImages(gno);
        Goods goods = result.orElseThrow();
        return entityToDto(goods);
    }



    //삭제
    @Override
    @Transactional
    public void modifyDelFlag(Long gno, boolean delFlag) {

        goodsRepository.updateToDelete(gno, delFlag);
    }



    //수정
    @Override
    public void modify(GoodsDTO goodsDTO) {

        //조회
        Optional<Goods> result = goodsRepository.findById(goodsDTO.getGno());

        Goods goods = result.orElseThrow();
        //변경 내용 반영
        goods.changeTitle(goodsDTO.getTitle());
        goods.changeGdesc(goodsDTO.getGdesc());
        goods.changePlace(goodsDTO.getPlace());
        goods.changeAge(goodsDTO.getAge());
        goods.changeGenre(goodsDTO.getGenre());
        goods.changeRunningTime(goodsDTO.getRunningTime());
        goods.changeExclusive(goodsDTO.isExclusive());
        goods.changeStartDate(goodsDTO.getStartDate());
        goods.changeEndDate(goodsDTO.getEndDate());
        goods.changeDel(goodsDTO.isDelFlag());

        //이미지 처리

        //이미지 가져오기
        List<String> uploadFileNames = goodsDTO.getUploadFileNames();

        //목록 비우기
        goods.clearList();

        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {
            uploadFileNames.forEach(goods::addImageString);
        }


        List<String> uploadTimes = goodsDTO.getTimes();
        goods.clearTimeList();
        if (uploadTimes != null && !uploadTimes.isEmpty()) {
            uploadTimes.forEach(goods::addTimes);
        }

        //저장
        goodsRepository.save(goods);

    }


    //dto를 entity로 변환
    private Goods dtoToEntity(GoodsDTO goodsDTO){

        Goods goods = Goods.builder()
                .gno(goodsDTO.getGno())
                .title(goodsDTO.getTitle())
                .gdesc(goodsDTO.getGdesc())
                .place(goodsDTO.getPlace())
                .startDate(goodsDTO.getStartDate())
                .endDate(goodsDTO.getEndDate())
                .age(goodsDTO.getAge())
                .runningTime(goodsDTO.getRunningTime())
                .genre(goodsDTO.getGenre())
                .exclusive(goodsDTO.isExclusive())
                .build();

        //업로드가 끝나면 나오는 uploadFileNames을
        List<String> uploadFileNames = goodsDTO.getUploadFileNames();

        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {
            uploadFileNames.forEach(goods::addImageString);
        }

        //공연 시간표
        List<String> times = goodsDTO.getTimes();
        if (times != null && !times.isEmpty()) {
            times.forEach(goods::addTimes);
        }
        return goods;
    }

    //entitiy->dto 변환 (조회 시 사용)
    private GoodsDTO entityToDto(Goods goods){
        GoodsDTO goodsDTO = GoodsDTO.builder()
                .gno(goods.getGno())
                .title(goods.getTitle())
                .gdesc(goods.getGdesc())
                .place(goods.getPlace())
                .startDate(goods.getStartDate())
                .endDate(goods.getEndDate())
                .age(goods.getAge())
                .runningTime(goods.getRunningTime())
                .genre(goods.getGenre())
                .delFlag(goods.isDelFlag())
                .exclusive(goods.isExclusive())
                .build();

        List<GoodsImage> imageList = goods.getImageList();

        if (imageList != null && !imageList.isEmpty()) {
            List<String> fileNameList = imageList.stream().map(GoodsImage::getFileName).toList();
            goodsDTO.setUploadFileNames(fileNameList);
        }


        List<GoodsTime> timeList = goods.getTimeList();
        if (timeList != null && !timeList.isEmpty()) {
            List<String> timeListToString = timeList.stream().map(GoodsTime::getTime).toList();
            goodsDTO.setTimes(timeListToString);
        }

        return goodsDTO;
    }
}
