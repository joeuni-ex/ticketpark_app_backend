package org.zerock.ticketapiserver.dto;


import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long reno;

    private String content; //예약일자

    private int likes; //좋아요

    private int grade;//평점

    private Long rno;

    private String email;//리뷰 작성한 회원

    private Long gno;//

    private LocalDate createDate;

    private boolean deleteFlag;


    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDate.now();
    }


}
