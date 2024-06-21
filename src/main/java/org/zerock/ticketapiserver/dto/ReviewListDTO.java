package org.zerock.ticketapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListDTO {

    private Long reno;

    private String content; //예약일자

    private int likes; //좋아요


    private int grade;//평점

    private String reservationDate;

    private String imageFile; //이미지

    private Long gno;

    private String goods_title;

    private LocalDate createDate;

    private boolean deleteFlag;

}
