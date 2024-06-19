package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.*;
import org.zerock.ticketapiserver.dto.ReviewDTO;
import org.zerock.ticketapiserver.repository.ReviewRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    //추가
    @Override
    public void register(ReviewDTO reviewDTO) {

        reviewRepository.save(dtoToEntity(reviewDTO));

    }

    //좋아요 +1추가
    @Override
    public void changeIncreaseLikes(Long reno) {
        Optional<Review> result = reviewRepository.findById(reno);

        Review review = result.get();

        review.changeIncreaseLikes(review.getLikes());

        reviewRepository.save(review);

    }

    
    

    
    

    // DTO -> 엔티티 변환 (저장 시 사용)
    private Review dtoToEntity(ReviewDTO reviewDTO) {

        Member member = Member.builder().email(reviewDTO.getEmail()).build();
        Goods goods = Goods.builder().gno(reviewDTO.getGno()).build();

        Review review = Review.builder()
                .owner(member)
                .goods(goods)
                .content(reviewDTO.getContent())
                .likes(reviewDTO.getLikes())
                .createDate(reviewDTO.getCreateDate())
                .build();

        return review;
    }

    //entitiy->dto 변환 (조회 시 사용)
    private ReviewDTO entityToDto(Review review){


        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reno(review.getReno())
                .email(review.getOwner().getEmail())
                .gno(review.getGoods().getGno())
                .content(review.getContent())
                .likes(review.getLikes())
                .createDate(review.getCreateDate())
                .build();


        return reviewDTO;
    }


}
