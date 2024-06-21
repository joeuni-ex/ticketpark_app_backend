package org.zerock.ticketapiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.ticketapiserver.domain.Review;
import org.zerock.ticketapiserver.dto.*;
import org.zerock.ticketapiserver.service.ReviewService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {


    private final ReviewService reviewService;


    //리뷰 추가
    @PostMapping("/")
    public Map<String,String> register(ReviewDTO reviewDTO ) {

        log.info(reviewDTO);

        reviewService.register(reviewDTO); //좌석 추가

        return Map.of("RESULT","SUCCESS");
    }


    //좋아요 1추가
    @PostMapping("/increase-likes/{reno}")
    public Map<String,String> changeIncreaseLikes(@PathVariable("reno") Long reno) {

        reviewService.changeIncreaseLikes(reno); //좋아요 추가

        return Map.of("RESULT","SUCCESS");
    }

    //수정 or 리뷰삭제
    @PreAuthorize("#reviewDTO.email == authentication.name") //현재 로그인한 사용자와 dto의 email 이 동일해야 사용가능함
    @PutMapping("/{reno}")
    public Map<String, String> modify(@PathVariable Long reno,  ReviewDTO reviewDTO){


        // 예약 취소
        if (reviewDTO.isDeleteFlag()) {
            ReviewDTO review = reviewService.get(reno);
            reviewService.modifyDeleteFlag(reno, reviewDTO.isDeleteFlag());
        }
        // 예약 변경
        else {
            reviewService.modify(reviewDTO); // 리뷰삭제
        }

        return Map.of("RESULT","SUCCESS");

    }


    // 목록 조회
    @GetMapping("/list")
    public List<ReviewListDTO> list(Principal principal) {
        String email = principal.getName(); // 현재 로그인 중인 유저의 정보

        log.info(email);

        return reviewService.getReviews(email);

    }


}
