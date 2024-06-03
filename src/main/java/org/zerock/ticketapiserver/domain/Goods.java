package org.zerock.ticketapiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString(exclude = "imageList") //엘리먼트 컬렉션이나 연관관계면 exclude로 빼줘야함
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_goods")
public class Goods {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 500,nullable = false)
    private  String title;

    private String place;

    private  String gdesc;

    private  int time;

    private int age;

    private String genre;

    private boolean delFlag;

    //굿즈 이미지
    @ElementCollection
    @Builder.Default
    private List<GoodsImage> imageList = new ArrayList<>();


    //제목 변경
    public void changeTitle (String title){
        this.title =title;
    }

    //장소 변경
    public void changePlace (String place){
        this.place =place;
    }

    //장소 변경
    public void changeGdesc (String gdesc){
        this.gdesc =gdesc;
    }


    //시간 변경
    public void changeTime (int time){
        this.time =time;
    }


    //나이 변경
    public void changeAge (int age){
        this.age =age;
    }


    //장르 변경
    public void changeGenre (String genre){
        this.genre =genre;
    }



    //이미지 추가
    public void addImage(GoodsImage image){

        image.setOrd(imageList.size());
        imageList.add(image);

    }

    //이미지 문자열 타입으로 파일 이름 추가
    public void addImageString(String fileName){

        GoodsImage productImage = GoodsImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);

    }

    public void clearList(){

        this.imageList.clear();

    }
}