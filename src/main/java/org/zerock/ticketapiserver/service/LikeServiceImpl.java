package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.domain.Like;
import org.zerock.ticketapiserver.domain.Member;
import org.zerock.ticketapiserver.domain.Review;
import org.zerock.ticketapiserver.repository.LikeRepository;
import org.zerock.ticketapiserver.repository.ReviewRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final ReviewRepository reviewRepository;


    private final LikeRepository likeRepository;

    //좋아요 없으면 추가 , 있으면 삭제
    @Override
    public void changeLikes(Long reno, String email) {
        Optional<Like> existingLike = likeRepository.findByReviewAndOwner(reno, email);

        if (existingLike.isPresent()) {
            // 만약 좋아요 추가되어있으면 삭제
            likeRepository.delete(existingLike.get());
            //  review-> likes 감소
            decreaseLikes(reno);
        } else {
            // 좋아요 추가되어 있지 않으면 추가
            Review review = reviewRepository.findById(reno)
                    .orElseThrow(() -> new IllegalArgumentException("Review not found for ID: " + reno));

            Member member = Member.builder().email(email).build();
            Like like = Like.builder()
                    .owner(member)
                    .review(review)
                    .build();
            likeRepository.save(like);
            // review-> likes 증가
            increaseLikes(reno);
        }
    }
    //좋아요 +1
    private void increaseLikes(Long reno) {
        Review review = reviewRepository.findById(reno)
                .orElseThrow(() -> new IllegalArgumentException("Review not found for ID: " + reno));
        review.changeIncreaseLikes(review.getLikes() + 1);
        reviewRepository.save(review);
    }
    //좋아요 -1
    private void decreaseLikes(Long reno) {
        Review review = reviewRepository.findById(reno)
                .orElseThrow(() -> new IllegalArgumentException("Review not found for ID: " + reno));
        review.changeIncreaseLikes(review.getLikes() - 1);
        reviewRepository.save(review);
    }

}
