# 🥬 멋쟁이채소처럼 👩‍🌾

- 프로젝트 기한: 2023-01-13 ~ 2023-02-15 (아이디어톤, 기능구현, 문서화, 시연영상)
- 팀 구성원: FrontEnd: 2명 / BackEnd: 6명 / 인프라: 1명
- 프로젝트 일정: [WBS](https://docs.google.com/spreadsheets/d/1G3FKDs14-A7BZXuUT2Fu2Iy52dS4YALAwxrtPA3YnfI/edit?usp=sharing)
- UI 설계: [view](https://docs.google.com/presentation/d/1r4G6jzTn3J_QtFfZ5NC_oEgf0kLwH6yBJwPslcKgvz8/edit?usp=sharing)
- 함께한 팀원은 어떤 팀원이었나요?: 설문 진행(회고) - 비공개

## 📸 1. 팀 구성원

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

### 서비스 핵심 기능소개

- 사용자 분리 (검증 시스템)
    - 일반: 읽기 권한
    - 기업/농가(정회원): 전체 서비스 이용가능
    - 관리자: 전체 서비스 이용가능 + 사용자 관리
-

## 🗺️ 3. ERD

![](erd.png)

## 4. 시스템 아키텍처

<img width="1081" alt="image" src="https://user-images.githubusercontent.com/94329274/219214725-2c009a9d-e2d9-4b07-9f84-8ff0dfa6e02a.png">

## 🛠️ 5. 프로젝트 환경 및 주요 기능

🟩 BackEnd<br>

- 사용언어
    - Language/Skills: JAVA 11 <br>
    - Build tool: Gradle <br>
    - ORM: JPA <br>
    - Library: spring boot web, lombok, spring security, JWT<br>
    - Framework: Springboot 2.7.5<br>
    - DB: Mysql, RDS, Redis<br>
    - Server: AWS EC2<br>
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
🟩 API 문서화 :

- [Postman](https://documenter.getpostman.com/view/25565883/2s935sngt5 )
  <img width="1719" alt="image" src="https://user-images.githubusercontent.com/94329274/219223230-9b5652fb-7dad-45a5-93de-b590b66b055b.png">

---

## 6. 도메인 아키텍처

<img width="1251" alt="image" src="https://user-images.githubusercontent.com/94329274/219214546-143ae027-8c9c-4ade-ab8c-a5a74a921c03.png">

### 사용자 다이어그램

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