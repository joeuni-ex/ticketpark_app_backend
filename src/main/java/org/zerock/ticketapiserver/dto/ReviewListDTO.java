package org.zerock.ticketapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class ReviewListDTO {

    private Long reno;

    private String content; //예약일자

    private String nickname;

    private int likes; //좋아요


    private int grade;//평점

    private String reservationDate;

    private String imageFile; //이미지

    private Long gno;

    private String goods_title;

    private LocalDate createDate;

    private boolean deleteFlag;


    public ReviewListDTO(Long reno,String content,String nickname, int likes,int grade,String reservationDate,String imageFile, Long gno,String goods_title,LocalDate createDate,boolean deleteFlag) {

        this.reno = reno;
        this.content = content;
        this.nickname = nickname;
        this.likes = likes;
        this.grade = grade;
        this.reservationDate = reservationDate;
        this.imageFile = imageFile;
        this.gno = gno;
        this.goods_title = goods_title;
        this.createDate = createDate;
        this.deleteFlag = deleteFlag;

    }


}
