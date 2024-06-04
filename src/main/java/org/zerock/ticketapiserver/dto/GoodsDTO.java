package org.zerock.ticketapiserver.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GoodsDTO {

    private Long gno;

    private  String title;

    private String place;

    private  String gdesc;

    private  int time;

    private int age;

    private String genre;

    private boolean delFlag;

    //파일 업로드
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    //조회 시 사용
    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

}
