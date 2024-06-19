package org.zerock.ticketapiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ticketapiserver.dto.ReservationDTO;
import org.zerock.ticketapiserver.dto.ReviewDTO;
import org.zerock.ticketapiserver.service.ReviewService;

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

        reviewService.changeIncreaseLikes(reno); //좌석 추가

        return Map.of("RESULT","SUCCESS");
    }

}
