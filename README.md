# SpringBoot + React Project -TicketPark Project 
SpringBoot + React + KAKAO 인증 API 를 활용한 Fullstack 반응형 웹 프로젝트 

## 💻 프로젝트 소개
SpringBoot + Firebase 를 활용하여 제작한 공연 예매 및 관리 웹 사이트 TicketPark 입니다. 
권한은 관리자와 유저로 구성되어 있으며, 관리자의 경우 상품 관리/리뷰 관리 기능을 지원하며,   
유저의 경우 원하는 날짜와 좌석을 선택하여 공연 예매 및 예매 변경, 취소 / 리뷰 관리 기능을 지원하는 반응형 웹 프로젝트 입니다.  

<WEB - 메인화면 >
- ![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/e9f28277-fe71-48ec-83c6-d3a09cf86907)


## :raising_hand:개발인원
- 개인 프로젝트(1명)

## ⚙️개발환경
**Front-end**
- React 
- axios 라이브러리로 서버통신 관리
- Redux Toolkit 으로 전역 상태 관리
- tailwind CSS 와 styled Components 로 스타일

**Back-end**
- SpringBoot
- Jpa
- Rest Api
- MySQL
  
## 🛠️ERD
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/7b34142e-9e2e-4abf-8558-82236787b308)


## 📌주요기능

### 회원가입
- 아이디 중복확인을 통해 이미 가입된 아이디가 있는지 확인 가능

### 로그인
- 아이디 비밀번호 일치하지 않을 경우 에러메세지 출력
- jwt 토큰 인증서비스를 통해 유저 정보 확인 후 로그인 처리
- 카카오 인증으로 소셜 로그인 처리

* * *

### 카테고리 별 공연 조회
< WEB > 
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/212d18d5-4e83-4f3e-b981-d301d64cf7c4)


- 콘서트/뮤지컬/연극/클래식 별로 조회 가능
- 한 페이지에 10개씩 페이징 처리 최신 등록순으로 조회


### 공연 상세페이지 
<WEB> 
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/6bd00989-bf50-4be2-95f3-c6ad70ff0f82)
  
<MOBILE>
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/0ae6eced-eb70-43d4-aa0d-20076646ee83)


- 공연의 상세 정보 조회
- 캘린더는 오늘 날짜부터 GOODS -> END_DATE 까지만 선택 가능
- 회차 선택 후 예매하기 클릭 시 좌석 선택 모달 출력 ( 로그인 유저만 가능 )

#### 좌석 선택 모달
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/b579d309-cc8d-43d1-a953-9197f4e4cc1d)
- 이미 예약된 좌석은 선택 불가능 및 좌석 색상 다름
- 좌석 선택 시 등급에 따른 금액 산정
- 예매하기 클릭 시 예매 확정

- 해당 공연의 리뷰 목록 조회
- 하트 아이콘 클릭 시 좋아요 가능 및 이미 좋아요 되어 있을 경우 삭제 가능 
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/d5f5a143-ee3d-4db5-b69e-bd0b6a08880b)


* * *

### 관리자 페이지 (ROLE_ADMIN)
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/6dc2a5e5-ac36-4de4-b0a7-095844dabdf9)

#### 상품 목록
- 현재 등록된 상품 목록 조회
- 페이징 처리되어 한 페이지에 10개씩 최신 등록순으로 조회
- 카테고리 별로 필터링 가능
  
#### 상품 등록 
- 공연 정보 및 이미지 등록하여 상품 추가

#### 상품 수정 
- 기존에 등록된 상품 수정

#### 상품 삭제 
- UPDATE로 del_flag -> true로 변경하여 DB에서는 삭제되지 않도록 함
- 상품 목록을 가져올 때는 del_flag -> false 조건을 추가해서 삭제 처리 되지 않은 상품만 가져옴

#### 리뷰 관리
- 모든 유저가 작성한 리뷰 최신순으로 조회
- 적절하지 않은 리뷰는 삭제 가능 

* * *
### 마이페이지 (ROLE_USER)
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/7a34239d-9ac5-4d87-9e3f-9d94610f4712)

#### 예약 목록 
- 유저가 예약한 목록 확인 가능
- 상세보기 클릭 시 예약 취소 또는 변경 가능
- 예약일자가 지나면 리뷰 작성 버튼으로 변경 되어 리뷰 작성 가능

#### 예약 취소 
- 예약 취소 시 UPDATE 로 cancel_flag -> ture로 변경하여 DB에서 데이터 삭제 방지

#### 예약 변경
- 동일한 공연 건에 한하여 예약일자 및 회차, 좌석 변경
- 좌석의 경우 등급에 따라 금액이 다르기 때문에 차액 환산하여 표시

#### 리뷰 작성 모달
![image](https://github.com/joeuni-ex/ticketpark_app_backend/assets/141595215/5c6c7214-6db2-420a-a30c-567c383da59e)
- 예약한 공연일자가 지나면 리뷰 작성이 가능
- 별점과 후기 저장

#### 내가 작성한 리뷰 
- 유저가 작성한 리뷰 목록 확인 가능
- 리뷰의 삭제와 수정이 가능
- 삭제 시 UPDATE로 delete_flag -> true로 변경하여 DB에서는 데이터가 삭제되지 않도록 방지한다.


