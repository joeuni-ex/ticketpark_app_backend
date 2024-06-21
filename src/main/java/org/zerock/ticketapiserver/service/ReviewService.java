package org.zerock.ticketapiserver.service;


import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.ReviewDTO;
import org.zerock.ticketapiserver.dto.ReviewListDTO;

import java.util.List;

@Transactional
public interface ReviewService {

    //리뷰 조회
    ReviewDTO get(Long reno);

    //추가
    void register(ReviewDTO reviewDTO);

    //유저 별 리뷰 목록
    List<ReviewListDTO> getReviewsOfMemeber(String email);

    //굿즈 별 리뷰 목록
    List<ReviewListDTO> getReviewsOfGoods(Long gno);

    //수정
    void modify(ReviewDTO reviewDTO);

    //리뷰 삭제
    @Transactional
    void modifyDeleteFlag(Long reno, boolean cancelFlag);
}
