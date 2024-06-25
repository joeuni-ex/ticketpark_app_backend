package org.zerock.ticketapiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.zerock.ticketapiserver.domain.Review;
import org.zerock.ticketapiserver.dto.*;
import org.zerock.ticketapiserver.repository.ReviewRepository;
import org.zerock.ticketapiserver.service.LikeService;
import org.zerock.ticketapiserver.service.ReservationService;
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

    private final ReservationService reservationService;

    private final LikeService likeService;

    //조회
    @GetMapping("/{reno}")
    public ReviewDTO read(@PathVariable("reno") Long reno){

        return reviewService.get( reno);

    }


    //리뷰 추가
    @PostMapping("/")
    public Map<String,String> register(ReviewDTO reviewDTO ) {

        ReservationDTO reservationDTO = reservationService.get(reviewDTO.getRno());

        reviewDTO.setGno(reservationDTO.getGno());

        reviewService.register(reviewDTO); //좌석 추가

        return Map.of("RESULT","SUCCESS");
    }


    //좋아요 추가 및 삭제
    @PostMapping("/likes/{reno}")
    public Map<String,String> changeLikes(@PathVariable("reno") Long reno , Principal principal) {

        String email = principal.getName(); // 현재 로그인 중인 유저의 정보

        likeService.changeLikes(reno,email); //좋아요 추가

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
    public List<ReviewListDTO> list(MemberEmailDTO memberEmailDTO) {

        String email = memberEmailDTO.getEmail(); // 현재 로그인 중인 유저의 정보

        List<ReviewListDTO> result = List.of();

        if(email.length() == 0){
            log.info("관리자");
            result = reviewService.getList(); //모든 유저 리뷰 가져오기 최신순으로

        }else
        {
            log.info("유저");
            result = reviewService.getReviewsOfMemeber(email); // 현재 접속중인 유저의  리뷰 가져오기
        }

        return result;

    }


    //굿즈 별 리뷰 목록조회(비회원,회원별 좋아요 여부 체크 가능)
    @GetMapping("/list/{gno}")
    public List<?> listOfGoods(@PathVariable Long gno, MemberEmailDTO memberEmailDTO) {

        log.info("memberEmailDTO: " + memberEmailDTO);
        if (memberEmailDTO.getEmail() != null) {
            String email = memberEmailDTO.getEmail(); //현재 로그인 중인 유저의 정보가 있으면 특정리 리뷰의 좋아요 여부 체크
            return reviewService.getReviewsWithLikeStatus(gno, email);
        } else {
            return reviewService.getReviewsOfGoods(gno);
        }
    }

    //조회 - 예약 번호 별 리뷰를 작성했는지 여부 체크
    @GetMapping("/check-review")
    public CheckWrittenReviewDTO checkWrittenReview(CheckWrittenReviewDTO checkWrittenReviewDTO){

        log.info("checkWrittenReviewDTO: " + checkWrittenReviewDTO);
        return reviewService.checkWrittenReview(checkWrittenReviewDTO);

    }

}
