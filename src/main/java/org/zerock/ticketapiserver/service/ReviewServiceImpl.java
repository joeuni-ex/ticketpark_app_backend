package org.zerock.ticketapiserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.*;
import org.zerock.ticketapiserver.dto.ReviewDTO;
import org.zerock.ticketapiserver.dto.ReviewListDTO;
import org.zerock.ticketapiserver.dto.ReviewWithLikeStatusDTO;
import org.zerock.ticketapiserver.repository.LikeRepository;
import org.zerock.ticketapiserver.repository.ReviewRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final LikeRepository likeRepository;

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



    //유저 별 리뷰 목록
    @Override
    public List<ReviewListDTO> getReviewsOfMemeber(String email) {
        return reviewRepository.getReviewsOfReviewDtoByEmail(email);
    }

    //굿즈 별 리뷰 목록
    @Override
    public List<ReviewListDTO> getReviewsOfGoods(Long gno) {
        return reviewRepository.getReviewsOfReviewDtoByGno(gno);
    }

    //로그인 한 유저의 굿즈 별 리뷰 목록
    @Override
    public List<ReviewWithLikeStatusDTO> getReviewsWithLikeStatus(Long gno, String email) {
        List<Review> reviews = reviewRepository.findByGoodsGno(gno);

        List<Long> reviewIds = reviews.stream()
                .map(Review::getReno)
                .collect(Collectors.toList());

        List<Like> likes = likeRepository.findByOwnerEmailAndReviewRenoIn(email, reviewIds);

        return reviews.stream()
                .map(review -> {
                    boolean liked = likes.stream().anyMatch(like -> like.getReview().getReno().equals(review.getReno()));
                    return new ReviewWithLikeStatusDTO(
                            review.getReno(),
                            review.getContent(),
                            review.getOwner().getNickname(),
                            review.getLikes(),
                            review.getGrade(),
                            review.getReservation().getReservationDate(),
                            review.getGoods().getImageList().stream().filter(img -> img.getOrd() == 0).findFirst().orElse(null).getFileName(),
                            review.getGoods().getGno(),
                            review.getGoods().getTitle(),
                            review.getCreateDate(),
                            review.isDeleteFlag(),
                            liked
                    );
                })
                .collect(Collectors.toList());
    }



    //수정
    @Override
    public void modify(ReviewDTO reviewDTO) {
       Optional<Review> result = reviewRepository.findById(reviewDTO.getReno());


        if (result.isPresent()) {
            Review review = result.get();
            review.changeContent(reviewDTO.getContent());
            review.changeGrade(reviewDTO.getGrade());

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
        Reservation reservation = Reservation.builder().rno(reviewDTO.getRno()).build();
        Goods goods = Goods.builder().gno(reviewDTO.getGno()).build();

        Review review = Review.builder()
                .owner(member)
                .reservation(reservation)
                .goods(goods)
                .content(reviewDTO.getContent())
                .likes(reviewDTO.getLikes())
                .createDate(reviewDTO.getCreateDate())
                .deleteFlag(reviewDTO.isDeleteFlag())
                .grade(reviewDTO.getGrade())
                .build();

        return review;
    }

    //entitiy->dto 변환 (조회 시 사용)
    private ReviewDTO entityToDto(Review review){


        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reno(review.getReno())
                .email(review.getOwner().getEmail())
                .rno(review.getReservation().getRno())
                .gno(review.getGoods().getGno())
                .content(review.getContent())
                .likes(review.getLikes())
                .createDate(review.getCreateDate())
                .deleteFlag(review.isDeleteFlag())
                .grade(review.getGrade())
                .build();


        return reviewDTO;
    }


}
