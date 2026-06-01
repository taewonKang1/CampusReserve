# CampusReserve

Java 21 LTS, Servlet/JSP, H2 Database 기반 스터디룸 및 사물함 예약 시스템입니다.

본 프로젝트는 웹프로그래밍-I 학기말 과제로, Spring Framework 없이 pure Java 기반 MVC 패턴을 직접 구현하는 것을 목표로 합니다.

---

## 1. 프로젝트 소개

CampusReserve는 사용자가 날짜와 시간을 선택하여 스터디룸 또는 사물함을 예약하고 Mock 결제를 진행할 수 있는 웹 애플리케이션입니다.

관리자는 전체 예약 현황, 취소 요청, 자원 배치 현황, 매출 통계를 확인할 수 있습니다.

---

## 2. 핵심 목표

- Servlet/JSP 기반 MVC Pattern 직접 구현
- FrontController + Command Pattern 구현
- Service / DAO / DTO 계층 분리
- H2 embedded file DB 사용
- Tomcat JNDI DataSource + DBCP 사용
- JDBC 트랜잭션 기반 중복 예약 방지
- 일반 회원과 관리자 권한 분리
- JSP 파일을 WEB-INF/views 하위에 배치하여 직접 접근 차단

---

## 3. 기술 스택

| 구분 | 기술 |
|---|---|
| Language | Java 21 LTS |
| WAS | Apache Tomcat 10.1.x |
| Web | Jakarta Servlet, JSP, EL, JSTL |
| Architecture | MVC Pattern |
| Request Mapping | FrontController + Command Pattern |
| Database | H2 Database embedded file mode |
| DB Access | JDBC |
| Connection Pool | Tomcat JNDI DataSource + DBCP |
| Build | Maven WAR Project |

---

## 4. MVP 기능

### 4.1 일반 회원

- 회원가입
- 로그인
- 로그아웃
- 스터디룸 목록 조회
- 사물함 목록 조회
- 예약 가능 시간 조회
- 예약 신청
- Mock 결제
- 내 예약 현황 조회
- 예약 취소 요청

### 4.2 관리자

- 관리자 로그인
- 자원 배치 현황 조회
- 전체 예약 조회
- 취소 요청 승인/거절
- 매출 통계 조회

### 4.3 핵심 기능

- 동일 자원, 동일 시간대 중복 예약 방지
- JDBC 트랜잭션 처리
- SELECT FOR UPDATE 기반 DB Lock 처리

---

## 5. 제외 기능

본 프로젝트에서는 과제 핵심 구현에 집중하기 위해 다음 기능은 제외합니다.

- 실제 PG 결제 API
- QR 체크인
- SMS 인증
- 이메일 인증
- 소셜 로그인
- CSV 다운로드
- Spring Framework
- Spring Boot
- JPA/Hibernate

---

## 6. 프로젝트 구조

```text
campus-reserve/
├── README.md
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/campusreserve/
│   │   │       ├── controller/
│   │   │       ├── command/
│   │   │       ├── service/
│   │   │       ├── dao/
│   │   │       ├── dto/
│   │   │       ├── filter/
│   │   │       └── util/
│   │   ├── resources/
│   │   │   ├── schema.sql
│   │   │   └── data.sql
│   │   └── webapp/
│   │       ├── assets/
│   │       ├── META-INF/
│   │       │   └── context.xml
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── views/
```

---

## 7. 요청 처리 흐름

```text
Client Request
    ↓
FrontController (*.do)
    ↓
CommandFactory
    ↓
Command
    ↓
Service
    ↓
DAO
    ↓
H2 Database
    ↓
JSP View
```

---

## 8. H2 DB 설정

본 프로젝트는 H2 embedded file mode를 사용합니다.

```properties
jdbc:h2:file:~/campusreserve-db/campusreserve;MODE=MySQL;DATABASE_TO_UPPER=false
```

주의:

- `jdbc:h2:mem` 방식은 서버 재시작 시 데이터가 사라지므로 최종 시연용으로 사용하지 않습니다.
- DB 접근은 `DataSource`를 통해 수행합니다.
- DAO에서 직접 `DriverManager.getConnection()`만 사용하는 방식은 지양합니다.

---

## 9. 예약 중복 방지 방식

예약 가능한 시간대는 `resource_slots` 테이블에서 관리합니다.

예약 요청 시 처리 순서:

1. JDBC Connection 획득
2. `setAutoCommit(false)`로 트랜잭션 시작
3. 선택한 slot을 `SELECT ... FOR UPDATE`로 조회
4. slot 상태가 `AVAILABLE`인지 확인
5. 예약 정보 저장
6. slot 상태를 `RESERVED`로 변경
7. Mock 결제 정보 저장
8. commit
9. 오류 발생 시 rollback

---

## 10. 주요 URL

| URL | Method | 설명 |
|---|---|---|
| `/user/register.do` | GET/POST | 회원가입 |
| `/user/login.do` | GET/POST | 로그인 |
| `/user/logout.do` | GET | 로그아웃 |
| `/resources/study-rooms.do` | GET | 스터디룸 목록 |
| `/resources/lockers.do` | GET | 사물함 목록 |
| `/reservation/form.do` | GET | 예약 화면 |
| `/reservation/create.do` | POST | 예약 생성 |
| `/mypage/reservations.do` | GET | 내 예약 현황 |
| `/mypage/cancel-request.do` | POST | 예약 취소 요청 |
| `/admin/dashboard.do` | GET | 관리자 메인 |
| `/admin/layout.do` | GET | 자원 배치 현황 |
| `/admin/reservations.do` | GET | 전체 예약 |
| `/admin/cancel-requests.do` | GET | 취소 요청 목록 |
| `/admin/sales.do` | GET | 매출 통계 |

---

## 11. 역할 분담 기준

현재 단계에서는 프로젝트 수행에 필요한 주요 역할 영역만 우선 구분하였습니다.  
실제 개발 과정에서 세부 기능 구현 범위와 담당 인원은 팀원 간 협의를 통해 최종 배정할 예정입니다.

### 역할 영역 A

- Backend
- DB
- 예약 트랜잭션
- H2 / DBCP 설정
- Service / DAO 구현
- 중복 예약 방지 구현

### 역할 영역 B

- JSP View
- 회원 화면
- 마이페이지
- 관리자 화면
- 화면 캡처 및 보고서 정리

### 공동 담당

- 요구사항 분석
- ERD 검토
- 통합 테스트
- 최종보고서 작성
- 발표자료 작성

---

## 12. 실행 전 준비 사항

- Java 21 설치
- Apache Tomcat 10.1.x 설치
- Maven 설치
- IDE 설정
- H2 dependency 확인
- `META-INF/context.xml`의 DataSource 설정 확인

---

## 13. 최종 제출 산출물

- 프로젝트 소스코드
- README.md
- 프로젝트 설계도
- DB schema.sql
- DB data.sql
- 기능정의서
- 일정표
- R&R
- 중간 수행 보고서
- 최종보고서
- 발표자료
- 시연 자료
