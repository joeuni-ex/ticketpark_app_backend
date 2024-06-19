package org.zerock.ticketapiserver.service;


import jakarta.transaction.Transactional;
import org.zerock.ticketapiserver.dto.ReviewDTO;

@Transactional
public interface ReviewService {

    //추가
    void register(ReviewDTO reviewDTO);


    //좋아요 +1추가
    void changeIncreaseLikes(Long reno);
}
