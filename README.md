# 프로젝트: 로드메이커
## 개인 프로젝트: 0 부터 다시 해보기 | 서버
한 달간 진행했던 내용에 대해 되짚어보기
서비스 설명
- MVP
## 1. 기술 스택
|       **항목**       | **기술 스택**                                   |
|:------------------:|---------------------------------------------|
|      Language      | JAVA, SpringBoot                            |
|         DB         | Redis, MySQL                                |
|       API 명세       | PostMan                                     |
|         보안         | JWT, SpringSecurity                         |
|       CI/CD        | Github Actions, Code Deploy, S3, EC2, NginX |
|  Static Analysis   | Sonar Cloud                                 |
- Build: Gradle
- 초기 dependency: Spring Boot DevTools, Lombok, Spring Web, Spring Security, Spring Data JPA, Spring Data JDBC
## 2. 버전 정보
|    **항목**     | **버전 정보** |
|:-------------:|-----------|
|     JAVA      | v.17      |
|  Spring Boot  | v.3.1.3   |
## 3. Outline: 한 달간 구현된 것들
### 1. CI/CD 구축: Github Actions를 이용한 Blue/Green 배포
   - AWS 서비스 설정: Code Deploy, EC2 등 연동
   - Code Deploy를 이용한 코드 배포
   - NginX: 리버스 프록시
![CI/CD: Blue/Green 무중단 배포](./img/cide_image.png)
### 2. DB 설정 및 연결: MySQL & Redis
### 3. 기능 구현
   - 로그인, 회원 가입: JWT token, Spring Security
   - GPT API 이용한 로드맵 생성: 스레드 풀을 이용한 멀티스레드 api 호출
   - 블로그 인증
   - 게시글 검색
   - 댓글
### 4. 섬네일 이미지 최적화
   - Spring Boot와 AWS S3 연동: 온디맨드 리사이징
## 3. Development(2023.10, 팀 프로젝트로 진행)
1. 기능 추가
   1. 로그인 기능: OAuth2 사용한 소셜 로그인
   2. 로드맵 생성 결과 캐싱(+ Elastic Search), 멀티스레딩 부담을 덜어줄 것으로 기대
   3. 그 외 논의 중
2. 개선
   1. GPT API 멀티스레딩: 많은 사람이 사용하는 경우에 대하여
   2. 멘토링 중 지적 받았던 내용
      1. 댓글
         - 페이지네이션 하면서 페이지 내에서 댓글의 일련 번호를 달아달라는 요청을 받았었음.
         - 프론트에서 해달라는대로 하였으나, 이렇게 구현하면 대댓이나 댓글을 삭제하는 기능을 구현하기 위해 다시 수정이 필요함: 결국 일을 다시 해야…
      2. 페이지네이션
         - 처음 페이지네이션을 구현할 때 프론트엔드에서 요청한 대로 Previous page, Next page URL을 전달해주는 API를 작성하였으나,
         - 이 방법은 NO Offset방식으로, 쿼리 속도를 높이기 위한 방법으로 사용되는 것이다. 즉, 아무런 생각없이 하라는대로 한 것인데, 그렇다보니 No Offset 방식이 아닌 전달 데이터만 Previous Page, Next Page를 보여주는 가짜 No Offset방식으로 구현된 것이다: https://jojoldu.tistory.com/528
         - 왜 이렇게 해야하는지 제대로 파악하면 무늬만 구현하거나 잘못된 구현을 막고 두 번 일하는 것을 막을 수 있다.
   3. QueryDSL: 검색 기능 유연화
   4. 로드맵 작성 기능 자체에 대한 폴리싱 필요: AI 프롬프팅
        - Elastic Search를 통해 캐시된 데이터를 가져옴으로써 답변 시간을 단축
        - 사용자가 늘고 좋은 데이터가 많아질수록 GPT 요금과 태생적으로 있을 수밖에 없는 응답시간에 대한 해결이 될 수 있을 것으로 예상하고 있다.
        - **하지만 로드맵 자체에 대한 폴리싱이 더 필요하기 때문에 결국 구현해 보아야 어떤 문제가 있고 어떻게 해결할지 실질적으로 고민할 수 있을 것 같다.**
3. 코드 리팩터링: 논의 예정
4. 서비스 제공 예정
## 4. Etc
- Convention: 협업 시 설정 필요, 협업 시 시행해보자.
  - Commit Convention
  - Branch Convention
  - PR Convention
  - Code Convention
