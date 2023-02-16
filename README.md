# 🥬 멋쟁이채소처럼 👩‍🌾

<img width="599" alt="image" style="border-radius: 15px" src="https://user-images.githubusercontent.com/94329274/219257195-b96b0b7a-34e5-4c59-bdf2-7d7de5f6edf7.png">

- 프로젝트 기한: 2023-01-13 ~ 2023-02-15 (아이디어톤, 기능구현, 문서화, 시연영상)
- 팀 구성원: FrontEnd: 2명 / BackEnd: 6명 / 인프라: 1명
- 프로젝트 일정: [WBS](https://docs.google.com/spreadsheets/d/1G3FKDs14-A7BZXuUT2Fu2Iy52dS4YALAwxrtPA3YnfI/edit?usp=sharing)
- UI 설계: [view](https://docs.google.com/presentation/d/1r4G6jzTn3J_QtFfZ5NC_oEgf0kLwH6yBJwPslcKgvz8/edit?usp=sharing)
- 함께한 팀원은 어떤 팀원이었나요?: 설문 진행(회고) - 비공개

## 📸 1. 팀 구성원

<img width="1212" style="border-radius: 15px" alt="image" src="https://user-images.githubusercontent.com/94329274/219258250-146e323e-e42a-46aa-8ca0-31e094a53cbc.png">

| 팀원  |       Role        |                                                                                                   구현기능                                                                                                    |
|:---:|:-----------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| 김예진 |        CTO        |                                                    - 회원 가입 및 로그인/로그아웃, Security, oauth 2.0<br/> - 경매 / 모집 리스트 조회<br/> - 알림 (경매 / 모집 종료시, 기업 / 농가 좋아요)                                                     |
| 김정현 |      FE / BE      |                                                           - FE (8페이지)<br/> - 전자계약 API (이폼싸인)<br/> - 경매/모집 마감 로직<br/> - 경매/모집 게시글 상페페이지 조회<br/>                                                            |
| 장서윤 | 팀장 / PM / FE / BE |   - FE (17페이지)<br/> - 프로젝트 총괄<br/> - 결제 API (토스) 결제 및 환불(카드)<br/> - 사용자 포인트<br/> - 보증금 예치 서비스 (모집/경매)<br/> - 경매 시작 최저가 게시글 5위 조회<br/>- 마이페이지(기업/농가/공통/관리자)<br/> - 관리자 정산(계좌이체) / 예치금 관리<br/> - 관리자 회원가입   |
| 전수진 |      기획 / BE      |                                                                                               로고 및 발표자료 문서화                                                                                               |
| 최아영 |        BE         |                                                        - 모집 신청 및 완료<br/> - 입찰 신청<br/> - 문자 본인인증<br/> - 농산물 API, 지도 API<br/> - 기업/농가 리스트 조회 및 단건 조회                                                        |
| 서태건 |     인프라 / BE      |                                                                           - CI/CD<br/> - 게시글 등록(기업/농가) 및 유효성<br/> - 이미지 업로드 S3                                                                            |
| 한건주 |        BE         |                                            - 기업 회원 검증(사업자등록, 이메일, URL)<br/> - 이미지 업로드 S3<br/> - 사용자 간 쪽지 기능<br/> - 게시글 좋아요(모집/경매)<br/> - 지역별 농산물 평균 거래량/입찰가 조회                                            |

## 👨‍👩‍👧‍👧 2. 프로젝트 기획

### [농가 / 기업의 이익 증대를 도와주는 온라인 못난이 농산물 중개 플랫폼]

**🏢 상품 가치가 낮아 버려지는 못난이 농산물**을 **어디에, 얼마나** 있는지 알 수 있다면?<br>
👨🏽‍🌾 버려지는 농산물을 필요로하는 **수요처**를 알 수 있다면?<br>

---

### 서비스 미션

- 버려지는 농산물을 공판장에 판매하는 것 이상의 값을 받을 수 있을 것인가?
- 기업과 농가의 접촉 최소화 (사이트 내에서 검색/게시/모집/계약/정산 해결)
- 경매 최고 가능 입찰가와 최저가를 어떻게 선정할 것인가?
- 경매 참여시 낙찰자가 결제하지 않는 경우 피해를 최소화할 수 있는 방법이 있는가?
- 모집 완료 시 기업이 대금을 납부하지 않는 경우를 대비할 수 있는가?
- 소규모 농가를 어떻게 찾고 모집할 것인가?
- 농산물 데이터를 어떻게 실시간으로 받을 것인가?
- 기업이 직접 농가를 방문하지 않고 대지를 확인할 수 있는가?
- 많은 사용자의 동시성 처리를 어떻게 할 것인가?

---

## 목표 기능 (100% 구현)

- [X] 사용자 (일반 사용자)
    - 회원가입(OAuth 2.0)
    - 로그인(JWT)
    - 로그아웃(Redis)
    - Security 일반회원 권한 설정(등록 불가)
- [X] 정회원 전환 (기업/농가)<br>
  회원 검증 및 심사 시스템 (기업/농가)
    - 이메일 검증 (API)
    - 사이트 유효 여부 URL 검증
    - 사업자 등록 정보 검증 (국세청 사업자등록정보 API)
    - 회원 등록 (기업/농가)
- [X] 모집/경매 게시글 작성(기업/농가)<br>
    - 농산물 경매 게시물 등록(농가) 유효성 검사
    - 농사물 모집 게시물 등록(기업) 유효성 검사
- [X] 기업/농가 조회<br>
    - 등록된 기업/농가 목록 조회(리스트/페이징)
    - 등록된 기업/농가 상세페이지 조회
- [X] 경매/모집 조회<br>
    - 등록된 경매 목록 조회(리스트/페이징) 인기순, 최신순
    - 등록된 모집 목록 조회(리스트/페이징) 인기순, 최신순
    - 경매 상세페이지 조회
        - 경매 시스템 현황 조회
        - 최고가 입찰 기업 추첨 및 결과 조회
    - 모집 상세페이지 조회
        - 모집 글 상세 조회
        - 참여 신청 현황 조회
- [X] 전자계약(이폼사인 전자서명 API)<br>
    - 멋쟁이채소처럼 계약서 생성
    - 모집 전자서명
    - 경매 전자서명
    - 진행 상태 조회
    - 계약서 이메일 발송
- [X] 결제(카드)토스 API<br>
    - 사용자 결제 요청 정보 저장
    - 카드 결제 API
    - 전체 금액 환불
    - 결제 상세내역 로그 저장
    - 결제시 포인트 업데이트
    - 사용자 결제 요청 금액 & 결제 API 금액 일치 여부 검증
- [X] 포인트<br>
    - 결제 금액 포인트 전환
    - 사용자 포인트 목록 조회
    - 사용자 포인트 상세 페이지
    - 결제시 사용자 포인트 적립(정비례)
    - 사용자 포인트 상세 내역 로그 저장
    - 포인트 사용시 사용자 포인트 업데이트
- [X] 보증금<br>
    - 보증금 예치
    - 보증금 사용 내역 등록
    - 보증금 사용 내역 조회
    - 예치시 사용자 포인트 업데이트
- [X] 관리자 정산(계좌이체)<br>
    - 전자계약 체결 후 수수료 제외한 금액 정산
    - 관리자 정산 요청 페이지
    - 계좌이체 (토스 API)
- [X] 관리자<br>
    - 관리자 정산 내역 조회
    - 관리자 회원가입
- [X] 마이페이지(기업/농가)<br>
    - 작성한 경매글 리스트(농가 마이페이지)
    - 참여한 모집글 리스트(농가 마이페이지)
    - 작성한 모집글 리스트(기업 마이페이지)
    - 현재 입찰한 경매글 리스트(기업 마이페이지)
    - 사용자 포인트 이벤트 로그 최신순 조회
- [X] 알림<br>
    - 게시글 좋아요 알림
    - 경매 마감 알림
    - 모집 마감 알림
- [X] 모집<br>
    - 모집 신청
    - 모집 완료 전환
- [X] 입찰<br>
    - 입찰 신청
    - 입찰 완료 전환
- [X] 본인인증 API (휴대폰)<br>
- [X] 실시간 농산물 유통 정보<br>
    - 농산물 API (매일 업데이트)
    - 품목별 경매 최저가 5위
    - 지역별 품목 통계
- [X] 등록된 농가/기업 위치 정보(map)<br>
    - 농가 위치 지도 API 마커
    - 기업 위치 지도 API 마커
- [X] 게시글 좋아요<br>
- [X] 사용자 쪽지 기능<br>

---

## 서비스 핵심 기능소개

- 회원 인증·인가
    - 로그인
      -일반 로그인 – 폼 입력(ID/PW)
    - 소셜 로그인 – 구글 Oauth 2.0
- 로그아웃
    - Redis 적용(access token blacklist 처리)
      <br><br>
- 사용자 분리 (검증 시스템)
    1. 일반: 읽기 권한
    2. 기업/농가(정회원) 전환: 전체 서비스 이용가능
        - 사업장 등록 정보 검증
        - 이메일 검증
        - 유효한 사이트인지 URL 검증
    3. 관리자: 전체 서비스 이용가능 + 정산
       <br><br>
- 결제/포인트/보증금
    1. 사용자 결제 요청
    2. 결제 진행 후 포인트 적립 (1:1)
    3. 포인트 상세 내역 저장
    4. 보증금 예치
    5. 전자계약 체결 후 정산
    6. 사용자 포인트 차감
       <br><br>
- 계약서 생성
    - 이폼사인 전자계약 폼 임베딩
- 서명 현황 조회
    - 이폼사인 계약서 정보 API 호출
    - 계약서 정보 DB 저장
- 계약 완료 시
    - 경매, 모집 게시글 상태 마감
    - 입찰, 참여 상태 마감
      <br><br>

- 모집 ·경매 신청
    - 문자 인증
        - SMS API를 사용해 인증번호 발송
        - Redis 적용하여 인증번호 관리
          <br><br>

- 농산물 가격 정보
    - KAMIS의 Open API 크롤링
      <br><br>

- 기업·농가 위치 정보
    - 현재 등록된 기업/농가 위치 확인 가능
    - 클러스터러, 로드뷰, 기업·농가 상세페이지 연결

---

## 🗺️ 3. ERD

![](erd.png)

## 4. 시스템 아키텍처

<img width="1081" alt="image" src="https://user-images.githubusercontent.com/94329274/219214725-2c009a9d-e2d9-4b07-9f84-8ff0dfa6e02a.png">

## 🛠️ 5. 프로젝트 환경 및 주요 기능

🟩 BackEnd<br>

- 사용언어
  <img width="924" alt="image" src="https://user-images.githubusercontent.com/94329274/219252921-fc2e83b7-39dd-4430-aa20-538c7ad75f98.png">
  <img width="751" alt="image" src="https://user-images.githubusercontent.com/94329274/219253095-2dc836e4-0285-4cd0-b5b2-a5f2fbc8ecfc.png">
- Library
    - spring boot web
    - Lombok
    - Spring Validation
    - Spring security
    - JWT
    - Spring Jackson
    - Spring Data Redis
      <br>
- 사용한 API
    - 농산물 API
    - 지도 API
    - 채팅 API
    - Mail API
    - 인증 API
    - 국세청 사업자등록정보 진위확인 및 상태 조회 API
    - NAVER SMS API
    - 토스 결제 API
    - 전자결제 이폼싸인

🟩 FrontEnd : HTML / CSS / JavaScript / bootstrap / thymeleaf<br>
🟩 협업 도구 : Gitlab(Issue/MergeRequest) / Discode / slack / notion<br>

- Issue/MR
  <img width="1075" alt="image" src="https://user-images.githubusercontent.com/94329274/219258443-afd2ab26-d1b0-41b6-b2d9-6fd0f139ca4f.png">

🟩 API 문서화 :

- [Postman](https://documenter.getpostman.com/view/25565883/2s935sngt5 )
  <img width="1719" alt="image" src="https://user-images.githubusercontent.com/94329274/219223230-9b5652fb-7dad-45a5-93de-b590b66b055b.png">

---

## 6. 도메인 아키텍처

<img width="926" alt="image" src="https://user-images.githubusercontent.com/94329274/219228098-4385598a-bb83-4f73-8729-7614647fcf0e.png">

## 사용자 다이어그램

<img width="756" alt="image" src="https://user-images.githubusercontent.com/94329274/219216640-70e9a427-bcee-4fc9-8f8b-45115e712357.png">

## 8. 핵심 기능 다이어그램

1. 농산물 정보 API 호출
   <img width="1016" alt="image" src="https://user-images.githubusercontent.com/94329274/219215215-0969ffc6-3f99-4ac8-9171-3e67ee210633.png">

2.검증

- 사업자 등록 정보
  <img width="926" alt="image" src="https://user-images.githubusercontent.com/94329274/219215376-c986b543-29d8-4c89-a152-63a22a604823.png">
- 사용자 검증 URL
  <img width="905" alt="image" src="https://user-images.githubusercontent.com/94329274/219215478-b21d4947-b007-4bfa-8bb0-15ea2304a3fb.png">

3. 모집

   <img width="414" alt="image" src="https://user-images.githubusercontent.com/94329274/219215668-f433aff8-4139-4c32-bea6-9b027e64884c.png">

4. 전자계약
   <img width="584" alt="image" src="https://user-images.githubusercontent.com/94329274/219227979-7ad21094-2c2c-4239-9662-7c1eb3311996.png">

## 9. 에러 문제 해결

### 핵심 로직 - 소셜 로그인

**에러 상황**<br>
Spring security의 웹 보안 기능은 `WebSecurityConfigurerAdapter`를 extends하여 구현하였으나, <br>
springboot 버전 업그레이드 후 `SecurityFilterChain`이 Bean으로 등록되어 Oauth 2.0 소셜 로그인 과정에서 문제가 발생했다.<br>
기존에는 `defaultSuccessUrl`을 통해 로그인 성공 후 redirect할 URL을 설정했지만, 이 프로젝트에서는 적용되지 않는 현상이 있었다.

**해결**<br>
`SimpleUrlAuthenticationSuccessHandler`를 상속 받은 `OAuth2AuthenticationSuccessHandler`를 만들어 Oauth 로그인 성공 후의 처리를 가능하게 했다.
핸들러에서 `UriComponentsBuilder`를 사용하여 redirect할 주소를 생성하고, 원하는 주소로 send할 수 있도록 했다.

---

### 핵심 로직 - 정회원 인증(사업자 등록정보 확인 API)

**에러 상황**<br>
`JSONObject.put()`을 사용하여 데이터를 저장할 때, 데이터 순서 정렬 문제가 있었다. API 호출을 위해 `JSONObject`를 사용해야 했는데, API에 전달되는 JSON 데이터의 순서를 지켜주어야
했다.<br>

하지만 `JSONObject`는 `Map`처럼 데이터의 순서를 고려하지 않고, 할당된 데이터를 메모리에 저장한다.
따라서 `JSONObject`호출 시 내가 입력한 데이터를 입력 순서대로 가져오지 않고, 메모리에 저장된 순서대로(섞여서) 가져오는 상황이었다.

**해결**<br>
데이터의 순서를 지키기 위해, `JSONObject`에 입력 시 `LinkedHashMap`을 사용하였다. 요구하는 순서대로 JSON을 입력하여 API를 호출하였다.

---

### 핵심 로직 - 문자 본인 인증

**에러 상황**<br>
문자 인증을 위해 사용자가 농가 사용자인지 기업 사용자인지 확인하는 부분에서 `NullPointException`(NPE)이 발생했다. 기업 사용자일 경우 사용자 테이블의 농가 정회원 ID에 null 값이 들어가고,
농가 사용자일 경우 기업 정회원 ID에 null 값이 들어가는 상황이었다.

**해결**<br>
처음에는 `orElseThrow`를 사용했지만 `NullPointException`가 떴다. CompanyUser나 FarmUser가 null 값인데 getId를 해서 그런 것이었다. 따라서 id를 가져오지 않고
객체를 `Optional`로 감싸준 후 `orElseThrow`를 사용하여 해결했다.

---

### 핵심 기능 - 결제

**에러상황**<br>
`Transactional`은 기본적으로 `UnChecked` Exception Error만을 롤백하는데, 런타임 혹은 프레임워크, 서버에 의해 발생되는 오류가 이에 해당된다. <br>
하지만 결제에서는 클라이언트에 의해 다양한 상황으로 예외가 발생할 수 있다.<br>
예를 들어 잔액부족, 카드정지, 결제 요청 금액 불일치, 유효기간 등으로 발생한 예외는 에러로 간주하지 않기 때문에 롤백하지 않는다. 따라서, 데이터가 일부만 처리되거나 누락될 수 있다.

**해결**<br>
이런 경우에는 `Transactional` 어노테이션의 `rollbackFor` 옵션을 이용해서 롤백 할 `Exception` 클래스를 직접 정의해야 한다.
<br>이를 통해 `Transactional rollbackFor exception` 예외 처리를 할 수 있어서 클라이언트 문제가 발생하여도 rollback이 되어 데이터 일부가 저장되는 현상을 방지할 수 있었다.

**사용자 결제 요청시 고유 식별 아이디 생성**<br>
<userType + 요청 날짜 + UUID><br>
모든 테이블에서 PK 값을 IDENTITY로 설정 했다 (GenerationType.IDENTITY)
결제 요청 시 사용되는 orderId는 동시에 요청해도 다른 값이 나와야 하고
다른 여러 테이블에서 만들어도 고유한 값이 나와야 하기 때문에 UUID를 사용했다.

---

### 핵심 기능 - 전자 계약

**에러 상황**<br>
전자 계약 완료 후 정보를 가져오는 도중`LazyInitializationException`에러가 발생했다.<Br> 이는 프록시 객체를 통해 지연 로딩되는 객체를 가져오려고 할 때 이미 세션이 종료되어 발생하는
에러였다. <br>`FetchType.EAGER` 즉시 로딩을 이용하여 객체를 읽어오는데 성공하였으나, 이는 엔티티 간의 관계가 복잡해질 수록 성능 저하의 우려가 있었다.

**해결**<br>
`@Transactional` 어노테이션을 사용하여 지연 조회 시점까지 세션을 유지하여 정상적으로 데이터를 조회할 수 있었다.

---

### 핵심 기능 - 글 작성 시 유효성 검사

**에러 상황**<br>
글을 작성할 때 금액, 내용 등 필수 요소들을 누락하고 글을 작성해도 작성이 완료되는 상황이 발생했다.

**해결**<br>
@Valid 어노테이션을 통해 값을 받아 DTO에서 유효성 검사를 했다<<br>
유효성 검증을 통해 누락된 항목을 알려준다

---

## 10. 배포

- [Swagger](http://ec2-13-125-75-14.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/#/)

## 11. 사이트 맵 영상

- [유튜브 멋쟁이채소처럼](https://youtu.be/ca-rsBlvM20)

## 12. 프로젝트 팀 노션

- [I5E2 멋쟁이채소처럼](https://www.notion.so/Team-Project-cd79fc4a20e04e8b9b1effbc6169793d?pvs=4)