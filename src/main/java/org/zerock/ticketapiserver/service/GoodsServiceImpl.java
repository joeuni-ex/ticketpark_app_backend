package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.Goods;
import org.zerock.ticketapiserver.domain.GoodsImage;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.PageRequestDTO;
import org.zerock.ticketapiserver.dto.PageResponseDTO;
import org.zerock.ticketapiserver.repository.GoodsRepository;

import java.util.List;
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


        Page<Object[]> result = goodsRepository.selectList(pageable);

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
                    .age(goods.getAge())
                    .time(goods.getTime())
                    .genre(goods.getGenre())
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

}
