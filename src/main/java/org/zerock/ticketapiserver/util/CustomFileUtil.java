package org.zerock.ticketapiserver.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {
  @Value("${org.zerocok.upload.path}") //properties에서 지정한 파일 업로드 경로
  private String uploadPath;

  @PostConstruct
  public void init(){
      File tempFolder = new File(uploadPath);
      if(!tempFolder.exists()){

          tempFolder.mkdir();

      }

      uploadPath = tempFolder.getAbsolutePath();

      log.info("================================");
      log.info(uploadPath);
  }

  public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{


      if(files == null || files.size()==0){
          return null;
      }

      List<String> uploadNames = new ArrayList<>();

      for(MultipartFile file: files){

          String savedName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

          Path savePath = Paths.get(uploadPath,savedName);

        //파일 저장
        //getInputStream() -> 예외 처리 필수
        try {
        Files.copy(file.getInputStream(),savePath); //원본 파일 업로드

           //파일 타입 확인
            String contentType = file.getContentType();

            //이미지 인 경우에만 확인을 해서 섬네일 만듬
            if(contentType != null && contentType.startsWith("image")){

                Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);

                //섬네일 사이즈
                Thumbnails.of(savePath.toFile()).size(200,200).toFile(thumbnailPath.toFile());


            }


            uploadNames.add(savedName);

      } catch (IOException e) {
          throw new RuntimeException(e);
      }
      }
      return uploadNames;
  }

  
  public ResponseEntity<Resource> getFile(String fileName){
    //파일 이름으로 조회

    Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

    if(!resource.isReadable()){
        //default값
        resource = new FileSystemResource(uploadPath+File.separator+"default.jpg");
    }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type",Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().headers(headers
        ).body(resource);
}


public void deleteFiles(List<String> fileNames) {
    if(fileNames == null || fileNames.isEmpty()
    ){
        return;
    }

    fileNames.forEach(fileName -> {
        //섬네일 삭제
        String thumbnailFileName = "s_" +fileName;

        //섬네일 경로
        Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
        //원본 경로
        Path filePath = Paths.get(uploadPath, fileName);

        try {
            Files.deleteIfExists(filePath);
            Files.deleteIfExists(thumbnailPath);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    });
}
}
