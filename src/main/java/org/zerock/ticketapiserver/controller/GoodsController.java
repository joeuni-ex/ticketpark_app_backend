package org.zerock.ticketapiserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.ticketapiserver.dto.GoodsDTO;
import org.zerock.ticketapiserver.dto.PageRequestDTO;
import org.zerock.ticketapiserver.dto.PageResponseDTO;
import org.zerock.ticketapiserver.service.GoodsService;
import org.zerock.ticketapiserver.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


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

  //파일 목록 조회
  @GetMapping("/list")
  public PageResponseDTO<GoodsDTO> list(PageRequestDTO pageRequestDTO) {

    return goodsService.getList(pageRequestDTO);

  }

  //조회
  @GetMapping("/{gno}")
  public GoodsDTO read(@PathVariable("gno") Long gno){

    return goodsService.get(gno);

  }


}
