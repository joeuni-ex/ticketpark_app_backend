package org.zerock.ticketapiserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.PageRequestDTO;
import org.zerock.ticketapiserver.dto.PageResponseDTO;
import org.zerock.ticketapiserver.dto.ReservationDTO;
import org.zerock.ticketapiserver.service.GoodsService;
import org.zerock.ticketapiserver.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;


@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

  private final CustomFileUtil fileUtil;

  private final GoodsService goodsService;


  @PostMapping("/")
  public Map<String,Long> register(GoodsDTO goodsDTO) {

    log.info("register " + goodsDTO);

    List<MultipartFile> files = goodsDTO.getFiles();

    List<String> uploadedFileNames = fileUtil.saveFiles(files);

    goodsDTO.setUploadFileNames(uploadedFileNames);

    log.info(uploadedFileNames);

    Long gno = goodsService.register(goodsDTO);

    return Map.of("RESULT",gno);
  }
  

  
  //파일 조회
  @GetMapping("/view/{fileName}")
  public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName){
      return fileUtil.getFile(fileName);
  }

  // 목록 조회
  @GetMapping("/list")
  public PageResponseDTO<GoodsDTO> list(PageRequestDTO pageRequestDTO) {

    return goodsService.getList(pageRequestDTO);

  }

  //조회
  @GetMapping("/{gno}")
  public GoodsDTO read(@PathVariable("gno") Long gno){

    return goodsService.get(gno);

  }



  //수정
  @PutMapping("/{gno}")
  public Map<String, String> modify(@PathVariable Long gno, GoodsDTO goodsDTO){

    log.info("modify " + gno + " " + goodsDTO);
    // 삭제
    if (goodsDTO.isDelFlag()) {

      goodsService.modifyDelFlag(gno, goodsDTO.isDelFlag());
    }
    //수정
    else{
      //업로드 저장
      goodsDTO.setGno(gno);

      //old product Database saved Product
      GoodsDTO oldProductDTO = goodsService.get(gno);

      //file upload
      List<MultipartFile> files = goodsDTO.getFiles();
      List<String> currentUploadFileNames = fileUtil.saveFiles(files);

      //keep files String
      List<String> uploadedFileNames = goodsDTO.getUploadFileNames();

      if(currentUploadFileNames != null && !currentUploadFileNames.isEmpty()){

        uploadedFileNames.addAll(currentUploadFileNames);

      }

      goodsService.modify(goodsDTO);

      List<String> oldFileNames = oldProductDTO.getUploadFileNames();

      if(oldFileNames != null && oldFileNames.size() > 0){

        List<String> removeFiles =
                oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName) == -1 ).collect(Collectors.toList());

        fileUtil.deleteFiles(removeFiles);
      }

    }



    return Map.of("RESULT","SUCCESS");

  }


}
