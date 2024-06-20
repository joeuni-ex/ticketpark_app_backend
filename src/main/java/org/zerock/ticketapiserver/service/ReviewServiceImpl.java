package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
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

    //리뷰 조회
    @Override
    public ReviewDTO get(Long reno) {

        Optional<Review> result = reviewRepository.findById(reno);

        Review review = result.orElseThrow();

        return entityToDto(review);
    }

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


    //수정
    @Override
    public void modify(ReviewDTO reviewDTO) {
       Optional<Review> result = reviewRepository.findById(reviewDTO.getReno());


        if (result.isPresent()) {
            Review review = result.get();
            review.changeContent(reviewDTO.getContent());

            reviewRepository.save(review);

        } else {
            throw new IllegalArgumentException("Review not found for ID: " + reviewDTO.getReno());
        }

    }


    //리뷰 삭제
    @Override
    @Transactional
    public void modifyDeleteFlag(Long reno, boolean cancelFlag) {
        reviewRepository.updateToDelete(reno, cancelFlag);
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
