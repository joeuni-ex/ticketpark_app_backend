package org.zerock.ticketapiserver.service;


import jakarta.transaction.Transactional;

@Transactional
public interface LikeService {
    //좋아요 없으면 추가 , 있으면 삭제
    void changeLikes(Long reno, String email);
}
