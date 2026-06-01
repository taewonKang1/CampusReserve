# CampusReserve 프로젝트 설계도

> 웹프로그래밍-I 학기말 과제 제출 및 개발 기준 문서  
> 주제: 스터디룸 및 사물함 예약 시스템  
> 기술 스택: Java 21 LTS, Servlet, JSP, JSTL, JDBC, H2 Database  
> 아키텍처: FrontController + Command Pattern + Service/DAO/DTO Layered Architecture

---

## 1. PDF 과제 유의사항 정리

본 프로젝트는 업로드된 과제 안내 PDF의 요구사항을 기준으로 설계한다.  
과제 요구사항을 벗어나지 않도록 아래 사항을 최우선 기준으로 삼는다.

### 1.1 팀 구성

- 2인 1팀으로 프로젝트를 수행한다.
- 역할은 기능 영역 기준으로 우선 분리한다.
- 현재 단계에서는 역할 영역만 우선 구분하였으며, 실제 개발 시 세부 기능별 담당 인원은 팀원 간 협의를 통해 확정할 예정이다.
- 따라서 본 R&R은 초기 역할 분담 기준안이며, 개발 진행 상황과 기능별 난이도에 따라 조정될 수 있다.

### 1.2 선택 주제

본 팀은 다음 주제를 선택한다.

**스터디룸 및 사물함 예약 시스템**

설명:

- 사용자가 날짜와 시간을 선택하여 원하는 스터디룸 또는 사물함을 예약한다.
- 예약 후 결제 처리를 진행한다.
- 사용자는 본인의 예약 현황을 확인하고 취소 요청을 할 수 있다.
- 관리자는 좌석 및 사물함 배치 현황과 매출 통계를 확인할 수 있다.

### 1.3 반드시 구현해야 하는 핵심 기능

과제 PDF에서 요구하는 스터디룸 및 사물함 예약 시스템의 핵심 기능은 다음과 같다.

| 구분 | 요구 기능 | 본 프로젝트 반영 방식 |
|---|---|---|
| 회원 관리 | 일반 회원 및 관리자 권한 분리 | Session의 role 값으로 MEMBER / ADMIN 구분 |
| 예약 로직 | 중복 예약 방지를 위한 트랜잭션 처리 | JDBC 트랜잭션 + DB Lock 방식 사용 |
| 마이페이지 | 본인 예약 현황 확인, 취소 요청 | 내 예약 목록 조회 및 취소 요청 기능 구현 |
| 관리자 페이지 | 좌석 배치 현황 조회, 매출 통계 | 자원별 예약 상태 조회 및 결제 금액 집계 |

### 1.4 개발 방식 유의사항

과제는 Spring Framework 사용을 목표로 하지 않는다.  
따라서 다음 조건을 반드시 지킨다.

- Spring Framework 사용 금지
- Spring Boot 사용 금지
- JPA/Hibernate 사용 금지
- pure Java 기반 Servlet, JSP 중심으로 구현
- MVC Pattern을 직접 코드로 구현
- 비즈니스 로직과 DB 설계에 집중

### 1.5 아키텍처 요구사항

과제 PDF에서 제시한 아키텍처 요구사항은 다음과 같다.

#### Controller

- 단일 `FrontController` Servlet을 둔다.
- 모든 요청은 `*.do` 패턴으로 받는다.
- `Command` 패턴을 이용하여 각 요청을 Java 클래스로 분기한다.

#### View

- JSP, EL, JSTL 조합을 사용한다.
- JSP 파일은 사용자가 직접 URL로 접근하지 못하도록 `WEB-INF/views` 하위에 배치한다.
- 화면 이동은 반드시 FrontController를 통한 forward 방식으로 처리한다.

#### Model

- Layered Architecture를 적용한다.
- Service, DAO, DTO 계층을 구분한다.
- Service는 비즈니스 로직을 담당한다.
- DAO는 JDBC 기반 DB 접근을 담당한다.
- DTO는 계층 간 데이터 전달을 담당한다.

#### Database

- `Context.xml`에 Connection Pool, 즉 DBCP를 직접 설정한다.
- DAO에서는 `DataSource`를 통해 DB 연결을 관리한다.
- 직접 `DriverManager.getConnection()`만 사용하는 방식은 지양한다.

### 1.6 제출 일정

| 제출 항목 | 제출일 | 본 프로젝트 산출물 |
|---|---:|---|
| 기능 정의서, 일정표 및 R&R | 2026.05.27 | 본 설계 문서, README, 기능정의서 |
| 중간 수행 보고서 | 2026.06.08 | DB 설계, ERD, 테이블 정의서, 핵심 구현 현황 |
| 결과 발표 및 최종 보고서 | 2026.06.22 | 최종 소스코드, 최종보고서, 발표자료, 시연 자료 |

---

## 2. 프로젝트 개요

## 2.1 프로젝트명

**CampusReserve**

## 2.2 프로젝트 한 줄 소개

Java 21 LTS, Servlet/JSP, H2 DB를 기반으로 구현하는 스터디룸 및 사물함 예약·결제 시스템이다.

## 2.3 프로젝트 목적

본 프로젝트의 목적은 Spring Framework 없이 Java 웹 애플리케이션의 기본 동작 원리를 직접 구현하는 것이다.

특히 다음 역량을 보여주는 것을 목표로 한다.

- Servlet 기반 요청 처리
- JSP 기반 화면 출력
- FrontController 패턴 구현
- Command 패턴 구현
- Service / DAO / DTO 계층 분리
- JDBC 기반 DB 접근
- H2 DB 기반 데이터 저장
- DBCP 기반 Connection Pool 설정
- DB 트랜잭션을 활용한 중복 예약 방지
- 일반 회원과 관리자 권한 분리

## 2.4 프로젝트 선정 이유

스터디룸 및 사물함 예약 시스템은 단순 CRUD보다 한 단계 더 높은 구현 요소를 포함한다.

가장 중요한 부분은 **같은 시간대에 같은 자원을 여러 사용자가 동시에 예약할 수 없도록 막는 것**이다.

이를 위해 본 프로젝트는 단순히 예약 데이터를 insert하는 것이 아니라, 예약 가능한 시간대인 `resource_slots`를 별도 테이블로 관리하고, 예약 시 해당 slot을 DB Lock으로 잠근 뒤 예약을 생성한다.

즉, 이 프로젝트는 다음 평가 포인트를 명확하게 보여줄 수 있다.

- DB 설계
- 트랜잭션 처리
- 중복 예약 방지
- MVC 직접 구현
- 관리자/회원 권한 분리
- 사용자 화면과 관리자 화면 분리

---

## 3. 확정 MVP 범위

## 3.1 MVP 정의

본 프로젝트의 MVP는 다음과 같이 정의한다.

> 사용자가 로그인 후 스터디룸 또는 사물함을 선택하여 특정 날짜와 시간에 예약하고, Mock 결제까지 완료할 수 있으며, 관리자는 전체 예약 현황과 매출 통계를 확인할 수 있는 최소 완성형 시스템

## 3.2 MVP에 반드시 포함되는 기능

| 분류 | 기능 | MVP 포함 여부 |
|---|---|---|
| 회원 | 회원가입 | 포함 |
| 회원 | 로그인 | 포함 |
| 회원 | 로그아웃 | 포함 |
| 회원 | 일반 회원 / 관리자 권한 분리 | 포함 |
| 자원 | 스터디룸 목록 조회 | 포함 |
| 자원 | 사물함 목록 조회 | 포함 |
| 예약 | 날짜별 예약 가능 시간 조회 | 포함 |
| 예약 | 예약 신청 | 포함 |
| 예약 | 중복 예약 방지 | 포함 |
| 결제 | Mock 결제 처리 | 포함 |
| 마이페이지 | 내 예약 현황 조회 | 포함 |
| 마이페이지 | 예약 취소 요청 | 포함 |
| 관리자 | 자원 배치 현황 조회 | 포함 |
| 관리자 | 전체 예약 조회 | 포함 |
| 관리자 | 취소 요청 승인/거절 | 포함 |
| 관리자 | 매출 통계 조회 | 포함 |

## 3.3 MVP에서 제외하는 기능

아래 기능은 과제의 핵심 요구사항과 직접 관련이 적거나 구현 부담이 크므로 MVP에서 제외한다.

| 제외 기능 | 제외 사유 |
|---|---|
| 실제 PG 결제 API 연동 | 외부 API 연동보다 MVC와 DB 트랜잭션 구현이 과제 핵심 |
| QR 체크인 | 부가 기능이며 필수 요구사항 아님 |
| SMS 인증 | 외부 서비스 연동 필요 |
| 이메일 인증 | 외부 SMTP 설정 필요 |
| 소셜 로그인 | OAuth 구현은 과제 핵심 범위를 벗어남 |
| CSV 다운로드 | 관리자 편의 기능이지만 필수 아님 |
| 관리자 로그 세부 기록 | 핵심 요구사항 아님 |
| Spring Framework | 과제에서 pure Java 구현 요구 |
| Spring Boot | 과제에서 Spring Framework 미사용 요구 |
| JPA/Hibernate | DAO/JDBC 직접 구현 목표와 맞지 않음 |
| Thymeleaf | JSP/EL/JSTL 조합이 더 안정적이고 과제 요구와 직접 부합 |

## 3.4 MVP 성공 기준

MVP는 다음 조건을 만족하면 완료로 본다.

1. 사용자가 회원가입과 로그인을 할 수 있다.
2. 일반 회원과 관리자의 메뉴 및 접근 권한이 분리된다.
3. 사용자가 스터디룸 또는 사물함 목록을 조회할 수 있다.
4. 사용자가 날짜와 시간대를 선택해 예약할 수 있다.
5. 동일 자원, 동일 시간대에 중복 예약이 발생하지 않는다.
6. 예약 성공 시 Mock 결제 정보가 저장된다.
7. 사용자가 본인의 예약 내역을 조회할 수 있다.
8. 사용자가 예약 취소 요청을 할 수 있다.
9. 관리자가 전체 예약 현황을 조회할 수 있다.
10. 관리자가 취소 요청을 승인 또는 거절할 수 있다.
11. 관리자가 일별 또는 자원별 매출 통계를 조회할 수 있다.

---

## 4. 확정 기술 스택

## 4.1 기본 기술

| 구분 | 확정 기술 |
|---|---|
| Language | Java 21 LTS |
| WAS | Apache Tomcat 10.1.x |
| Web | Jakarta Servlet, JSP, EL, JSTL |
| Architecture | MVC Pattern |
| Controller 구조 | FrontController + Command Pattern |
| Model 구조 | Service / DAO / DTO |
| Database | H2 Database embedded file mode |
| DB Access | JDBC |
| Connection Pool | Tomcat JNDI DataSource + DBCP |
| Build | Maven WAR |
| View | JSP |
| Static Resource | CSS, JavaScript |

## 4.2 Java 21 LTS 사용 이유

Java 21은 LTS 버전이므로 안정적인 개발 환경을 구성할 수 있다.  
또한 최신 Java 문법과 성능 개선을 사용할 수 있으면서도, Servlet/JSP 기반 웹 애플리케이션 개발에 충분히 안정적이다.

## 4.3 Tomcat 10.1 사용 이유

Tomcat 10.1.x는 Jakarta Servlet 기반의 안정적인 WAS이다.  
Java 21과 함께 사용 가능하며, JSP/Servlet 기반 웹 프로젝트를 배포하기에 적합하다.

주의할 점:

- Tomcat 10 이상은 `jakarta.servlet.*` 패키지를 사용한다.
- 만약 수업 예제 코드가 `javax.servlet.*` 기반이면 Tomcat 9로 낮춰야 한다.
- 본 프로젝트는 Java 21 LTS와 Tomcat 10.1.x 조합을 기준으로 설계한다.

## 4.4 H2 Database 사용 이유

본 프로젝트에서는 H2 Database를 embedded file mode로 사용한다.

사용 이유:

- 별도 DB 서버 설치가 필요 없다.
- 팀원 간 개발 환경 차이를 줄일 수 있다.
- 제출 및 시연이 쉽다.
- JDBC 학습에 적합하다.
- 트랜잭션과 SELECT FOR UPDATE를 사용한 예약 중복 방지 구현이 가능하다.
- 향후 MySQL 또는 MariaDB로 전환 가능한 구조를 만들 수 있다.

사용할 JDBC URL 예시:

```properties
jdbc:h2:file:~/campusreserve-db/campusreserve;MODE=MySQL;DATABASE_TO_UPPER=false
```

주의:

- `jdbc:h2:mem:` 방식은 서버 재시작 시 데이터가 사라지므로 최종 제출 및 시연용으로 사용하지 않는다.
- 파일 기반 H2 DB를 사용하여 시연 데이터가 유지되도록 한다.

---

## 5. 전체 시스템 아키텍처

## 5.1 계층 구조

```text
Client Browser
    ↓
FrontController Servlet (*.do)
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
JSP View (/WEB-INF/views)
```

## 5.2 계층별 책임

| 계층 | 책임 |
|---|---|
| FrontController | 모든 요청 수신, URI 분석, Command 호출 |
| CommandFactory | URI에 맞는 Command 객체 생성 |
| Command | 요청별 실행 흐름 담당 |
| Service | 비즈니스 로직 처리 |
| DAO | SQL 실행 및 DB 접근 |
| DTO | 계층 간 데이터 전달 |
| JSP | 사용자 화면 출력 |
| Filter | 인코딩, 로그인 여부, 관리자 권한 확인 |

## 5.3 요청 처리 예시

예약 생성 요청 흐름:

```text
POST /reservation/create.do
    ↓
FrontController
    ↓
CreateReservationCommand
    ↓
ReservationService.createReservation()
    ↓
ReservationDAO / SlotDAO / PaymentDAO
    ↓
H2 DB Transaction
    ↓
reservation-complete.jsp
```

---

## 6. 디렉터리 구조 설계

```text
campus-reserve/
├── README.md
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/campusreserve/
│   │   │       ├── controller/
│   │   │       │   └── FrontController.java
│   │   │       ├── command/
│   │   │       │   ├── Command.java
│   │   │       │   ├── CommandFactory.java
│   │   │       │   ├── user/
│   │   │       │   ├── reservation/
│   │   │       │   ├── mypage/
│   │   │       │   └── admin/
│   │   │       ├── service/
│   │   │       │   ├── UserService.java
│   │   │       │   ├── ReservationService.java
│   │   │       │   ├── PaymentService.java
│   │   │       │   └── AdminService.java
│   │   │       ├── dao/
│   │   │       │   ├── UserDAO.java
│   │   │       │   ├── ResourceDAO.java
│   │   │       │   ├── SlotDAO.java
│   │   │       │   ├── ReservationDAO.java
│   │   │       │   └── PaymentDAO.java
│   │   │       ├── dto/
│   │   │       │   ├── UserDTO.java
│   │   │       │   ├── ResourceDTO.java
│   │   │       │   ├── SlotDTO.java
│   │   │       │   ├── ReservationDTO.java
│   │   │       │   └── PaymentDTO.java
│   │   │       ├── filter/
│   │   │       │   ├── EncodingFilter.java
│   │   │       │   ├── LoginCheckFilter.java
│   │   │       │   └── AdminCheckFilter.java
│   │   │       └── util/
│   │   │           └── DBUtil.java
│   │   ├── resources/
│   │   │   ├── schema.sql
│   │   │   └── data.sql
│   │   └── webapp/
│   │       ├── assets/
│   │       │   ├── css/
│   │       │   └── js/
│   │       ├── META-INF/
│   │       │   └── context.xml
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── views/
│   │               ├── common/
│   │               ├── user/
│   │               ├── reservation/
│   │               ├── mypage/
│   │               └── admin/
```

---

## 7. 기능 정의서

## 7.1 회원 기능

| 기능 ID | 기능명 | 설명 |
|---|---|---|
| U-01 | 회원가입 | 사용자가 아이디, 비밀번호, 이름, 연락처를 입력하여 가입한다. |
| U-02 | 로그인 | 아이디와 비밀번호를 검증한 후 session에 사용자 정보를 저장한다. |
| U-03 | 로그아웃 | session을 종료하여 로그아웃한다. |
| U-04 | 권한 분리 | MEMBER와 ADMIN 권한에 따라 접근 가능한 화면을 분리한다. |

## 7.2 자원 조회 기능

| 기능 ID | 기능명 | 설명 |
|---|---|---|
| RSC-01 | 스터디룸 목록 조회 | ACTIVE 상태의 스터디룸 목록을 조회한다. |
| RSC-02 | 사물함 목록 조회 | ACTIVE 상태의 사물함 목록을 조회한다. |
| RSC-03 | 예약 가능 시간 조회 | 특정 자원과 날짜를 기준으로 AVAILABLE 상태의 slot을 조회한다. |

## 7.3 예약 기능

| 기능 ID | 기능명 | 설명 |
|---|---|---|
| RSV-01 | 예약 신청 | 선택한 자원과 시간대를 기준으로 예약을 생성한다. |
| RSV-02 | 중복 예약 방지 | DB 트랜잭션과 SELECT FOR UPDATE로 동일 시간대 중복 예약을 방지한다. |
| RSV-03 | 예약 완료 조회 | 예약 성공 후 예약 번호, 시간, 금액을 보여준다. |

## 7.4 결제 기능

| 기능 ID | 기능명 | 설명 |
|---|---|---|
| PAY-01 | Mock 결제 | 실제 결제 API 없이 결제 성공 상태를 DB에 저장한다. |
| PAY-02 | 결제 내역 저장 | 예약별 결제 금액, 결제 수단, 결제 상태를 저장한다. |

## 7.5 마이페이지 기능

| 기능 ID | 기능명 | 설명 |
|---|---|---|
| MY-01 | 내 예약 현황 조회 | 로그인한 사용자의 예약 목록을 조회한다. |
| MY-02 | 예약 취소 요청 | 본인 예약에 대해 취소 요청을 등록한다. |

## 7.6 관리자 기능

| 기능 ID | 기능명 | 설명 |
|---|---|---|
| ADM-01 | 자원 배치 현황 조회 | 날짜별 스터디룸/사물함의 예약 상태를 조회한다. |
| ADM-02 | 전체 예약 조회 | 전체 사용자의 예약 내역을 조회한다. |
| ADM-03 | 취소 요청 관리 | 사용자의 취소 요청을 승인 또는 거절한다. |
| ADM-04 | 매출 통계 조회 | 일별, 자원별 결제 금액을 집계하여 조회한다. |

---

## 8. 화면 설계

## 8.1 공통 화면

| 화면 | JSP 경로 |
|---|---|
| 메인 화면 | `/WEB-INF/views/index.jsp` |
| 로그인 화면 | `/WEB-INF/views/user/login.jsp` |
| 회원가입 화면 | `/WEB-INF/views/user/register.jsp` |
| 오류 화면 | `/WEB-INF/views/common/error.jsp` |

## 8.2 회원 화면

| 화면 | JSP 경로 |
|---|---|
| 스터디룸 목록 | `/WEB-INF/views/reservation/study-room-list.jsp` |
| 사물함 목록 | `/WEB-INF/views/reservation/locker-list.jsp` |
| 예약 시간 선택 | `/WEB-INF/views/reservation/reservation-form.jsp` |
| 예약 완료 | `/WEB-INF/views/reservation/reservation-complete.jsp` |
| 내 예약 현황 | `/WEB-INF/views/mypage/my-reservations.jsp` |
| 취소 요청 | `/WEB-INF/views/mypage/cancel-request.jsp` |

## 8.3 관리자 화면

| 화면 | JSP 경로 |
|---|---|
| 관리자 메인 | `/WEB-INF/views/admin/dashboard.jsp` |
| 자원 배치 현황 | `/WEB-INF/views/admin/resource-layout.jsp` |
| 전체 예약 관리 | `/WEB-INF/views/admin/reservations.jsp` |
| 취소 요청 관리 | `/WEB-INF/views/admin/cancel-requests.jsp` |
| 매출 통계 | `/WEB-INF/views/admin/sales.jsp` |

---

## 9. URL 설계

## 9.1 회원 URL

| URL | Method | 설명 |
|---|---|---|
| `/user/register.do` | GET | 회원가입 화면 |
| `/user/register.do` | POST | 회원가입 처리 |
| `/user/login.do` | GET | 로그인 화면 |
| `/user/login.do` | POST | 로그인 처리 |
| `/user/logout.do` | GET | 로그아웃 처리 |

## 9.2 예약 URL

| URL | Method | 설명 |
|---|---|---|
| `/resources/study-rooms.do` | GET | 스터디룸 목록 |
| `/resources/lockers.do` | GET | 사물함 목록 |
| `/reservation/form.do` | GET | 예약 시간 선택 화면 |
| `/reservation/create.do` | POST | 예약 생성 |
| `/reservation/complete.do` | GET | 예약 완료 화면 |

## 9.3 마이페이지 URL

| URL | Method | 설명 |
|---|---|---|
| `/mypage/reservations.do` | GET | 내 예약 현황 |
| `/mypage/cancel-request.do` | POST | 예약 취소 요청 |

## 9.4 관리자 URL

| URL | Method | 설명 |
|---|---|---|
| `/admin/dashboard.do` | GET | 관리자 메인 |
| `/admin/layout.do` | GET | 자원 배치 현황 |
| `/admin/reservations.do` | GET | 전체 예약 조회 |
| `/admin/cancel-requests.do` | GET | 취소 요청 목록 |
| `/admin/cancel-approve.do` | POST | 취소 승인 |
| `/admin/cancel-reject.do` | POST | 취소 거절 |
| `/admin/sales.do` | GET | 매출 통계 |

---

## 10. DB 설계

## 10.1 ERD 개념

```text
users 1 ─── N reservations N ─── 1 resources
                         │
                         │ 1
                         │
                         N
                      payments

resources 1 ─── N resource_slots
reservations N ─── N resource_slots
```

단순화를 위해 MVP에서는 예약 1건이 하나 이상의 slot을 사용할 수 있도록 설계한다.  
다만 구현 난이도를 줄이기 위해 초기 구현은 1시간 단위 slot 하나 예약부터 시작하고, 이후 다중 slot 예약으로 확장할 수 있다.

## 10.2 테이블 목록

| 테이블 | 설명 |
|---|---|
| users | 회원 정보 |
| resources | 스터디룸/사물함 정보 |
| resource_slots | 예약 가능한 시간대 |
| reservations | 예약 정보 |
| reservation_slots | 예약과 slot 매핑 |
| payments | 결제 정보 |

## 10.3 users

```sql
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(30),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## 10.4 resources

```sql
CREATE TABLE resources (
    resource_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resource_type VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    capacity INT,
    price_per_slot INT NOT NULL,
    status VARCHAR(20) NOT NULL
);
```

## 10.5 resource_slots

```sql
CREATE TABLE resource_slots (
    slot_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resource_id BIGINT NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT uq_resource_slot UNIQUE (resource_id, start_at, end_at),
    CONSTRAINT fk_slot_resource FOREIGN KEY (resource_id) REFERENCES resources(resource_id)
);
```

## 10.6 reservations

```sql
CREATE TABLE reservations (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    resource_id BIGINT NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    total_price INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_reservation_resource FOREIGN KEY (resource_id) REFERENCES resources(resource_id)
);
```

## 10.7 reservation_slots

```sql
CREATE TABLE reservation_slots (
    reservation_id BIGINT NOT NULL,
    slot_id BIGINT NOT NULL,
    PRIMARY KEY (reservation_id, slot_id),
    CONSTRAINT fk_rs_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id),
    CONSTRAINT fk_rs_slot FOREIGN KEY (slot_id) REFERENCES resource_slots(slot_id)
);
```

## 10.8 payments

```sql
CREATE TABLE payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_id BIGINT NOT NULL,
    amount INT NOT NULL,
    method VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    paid_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);
```

---

## 11. 예약 중복 방지 설계

## 11.1 문제 상황

동일한 스터디룸 또는 사물함에 대해 두 사용자가 거의 동시에 예약 요청을 보낼 수 있다.

예시:

- 사용자 A: 6월 10일 10:00 ~ 11:00, 스터디룸 A 예약
- 사용자 B: 6월 10일 10:00 ~ 11:00, 스터디룸 A 예약

단순히 예약 전 조회 후 insert하는 방식이면 두 요청이 모두 성공할 수 있다.  
따라서 DB 트랜잭션과 Lock을 사용하여 반드시 한 명만 예약 성공하도록 처리한다.

## 11.2 해결 방식

본 프로젝트는 `resource_slots` 테이블의 행을 기준으로 예약 가능 여부를 관리한다.

예약 요청 시 다음 순서로 처리한다.

1. JDBC Connection을 가져온다.
2. `setAutoCommit(false)`로 트랜잭션을 시작한다.
3. 선택한 slot을 `SELECT ... FOR UPDATE`로 조회하여 잠근다.
4. slot 상태가 `AVAILABLE`인지 확인한다.
5. 사용 가능하면 `reservations`에 예약을 생성한다.
6. `reservation_slots`에 예약-slot 매핑을 저장한다.
7. `resource_slots` 상태를 `RESERVED`로 변경한다.
8. `payments`에 Mock 결제 정보를 저장한다.
9. 모든 작업이 성공하면 `commit`한다.
10. 오류 발생 시 `rollback`한다.

## 11.3 의사 코드

```java
Connection conn = null;

try {
    conn = DBUtil.getConnection();
    conn.setAutoCommit(false);

    SlotDTO slot = slotDAO.findByIdForUpdate(conn, slotId);

    if (!"AVAILABLE".equals(slot.getStatus())) {
        throw new IllegalStateException("이미 예약된 시간입니다.");
    }

    long reservationId = reservationDAO.insert(conn, reservationDTO);
    reservationSlotDAO.insert(conn, reservationId, slotId);
    slotDAO.updateStatus(conn, slotId, "RESERVED");
    paymentDAO.insertMockPayment(conn, reservationId, amount);

    conn.commit();

} catch (Exception e) {
    if (conn != null) {
        conn.rollback();
    }
    throw e;

} finally {
    if (conn != null) {
        conn.close();
    }
}
```

## 11.4 테스트 기준

중복 예약 방지 기능은 다음 방식으로 검증한다.

1. 서로 다른 두 계정으로 로그인한다.
2. 같은 자원과 같은 시간대를 선택한다.
3. 두 브라우저에서 거의 동시에 예약 버튼을 클릭한다.
4. 한 계정만 예약 성공해야 한다.
5. 다른 계정은 "이미 예약된 시간입니다." 메시지를 받아야 한다.
6. DB에는 해당 시간대 예약이 1건만 존재해야 한다.
7. 해당 slot의 상태는 `RESERVED`로 변경되어야 한다.

---

## 12. H2 DB 설정

## 12.1 H2 JDBC URL

```properties
jdbc:h2:file:~/campusreserve-db/campusreserve;MODE=MySQL;DATABASE_TO_UPPER=false
```

## 12.2 Maven 의존성 예시

```xml
<dependencies>
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>6.0.0</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
        <version>3.0.2</version>
    </dependency>

    <dependency>
        <groupId>org.glassfish.web</groupId>
        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
        <version>3.0.1</version>
    </dependency>

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>2.4.240</version>
    </dependency>
</dependencies>
```

## 12.3 context.xml 예시

`src/main/webapp/META-INF/context.xml`

```xml
<Context>
    <Resource
        name="jdbc/CampusReserveDB"
        auth="Container"
        type="javax.sql.DataSource"
        driverClassName="org.h2.Driver"
        url="jdbc:h2:file:~/campusreserve-db/campusreserve;MODE=MySQL;DATABASE_TO_UPPER=false"
        username="sa"
        password=""
        maxTotal="20"
        maxIdle="10"
        maxWaitMillis="10000" />
</Context>
```

## 12.4 web.xml resource-ref 예시

```xml
<resource-ref>
    <description>CampusReserve H2 DataSource</description>
    <res-ref-name>jdbc/CampusReserveDB</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
</resource-ref>
```

---

## 13. 개발 일정

## 13.1 전체 일정

| 단계 | 기간 | 목표 |
|---|---|---|
| 1단계 | 2026.05.27 | 기능정의서, 일정표, R&R 제출 |
| 2단계 | 2026.05.28 ~ 2026.06.03 | 프로젝트 구조 및 DB 기본 설계 |
| 3단계 | 2026.06.04 ~ 2026.06.10 | 회원/예약/결제 핵심 기능 구현 |
| 4단계 | 2026.06.11 ~ 2026.06.17 | 마이페이지 및 관리자 기능 구현 |
| 5단계 | 2026.06.18 ~ 2026.06.22 | 테스트, 최종보고서, 발표자료 작성 |

## 13.2 세부 일정

| 기간 | 작업 |
|---|---|
| 05.27 | 기능정의서, 일정표, R&R 제출 |
| 05.28 ~ 05.30 | Java 21, Tomcat 10.1, Maven WAR 프로젝트 설정 |
| 05.30 ~ 06.01 | FrontController, Command, Service/DAO/DTO 기본 구조 작성 |
| 06.01 ~ 06.03 | H2 DB, DBCP, schema.sql, data.sql 작성 |
| 06.04 ~ 06.06 | 회원가입, 로그인, 로그아웃 구현 |
| 06.06 ~ 06.08 | 예약 가능 시간 조회, 예약 생성, 트랜잭션 처리 구현 |
| 06.08 | 중간 수행 보고서 제출 |
| 06.09 ~ 06.10 | Mock 결제 및 예약 완료 화면 구현 |
| 06.11 ~ 06.13 | 마이페이지 예약 현황 및 취소 요청 구현 |
| 06.14 ~ 06.17 | 관리자 예약 관리, 취소 처리, 매출 통계 구현 |
| 06.18 ~ 06.20 | 중복 예약 테스트 및 오류 수정 |
| 06.20 ~ 06.21 | 최종보고서, 발표자료, 시연 시나리오 작성 |
| 06.22 | 결과 발표 및 최종 제출 |

---

## 14. R&R

## 14.1 역할 배정 방식

현재 단계에서는 프로젝트 수행에 필요한 주요 역할 영역만 우선 구분하였다.  
실제 개발 과정에서 세부 기능 구현 범위와 담당 인원은 팀원 간 협의를 통해 최종 배정할 예정이다.  
따라서 본 R&R은 초기 역할 분담 기준안으로 작성되었으며, 개발 진행 상황과 기능별 난이도에 따라 조정될 수 있다.

## 14.2 역할 영역 A: Backend / DB / 예약 트랜잭션

담당 범위:

- Maven WAR 프로젝트 설정
- Tomcat 10.1 연동
- H2 embedded file DB 설정
- DBCP DataSource 설정
- FrontController 구현
- CommandFactory 구현
- Service/DAO/DTO 기본 구조 작성
- DB 테이블 설계
- 예약 가능 시간 조회
- 예약 생성 기능
- SELECT FOR UPDATE 기반 중복 예약 방지
- Mock 결제 처리
- 매출 통계 SQL 작성

주요 산출물:

- `FrontController.java`
- `CommandFactory.java`
- `ReservationService.java`
- `ReservationDAO.java`
- `SlotDAO.java`
- `PaymentDAO.java`
- `schema.sql`
- `context.xml`
- 중복 예약 테스트 결과

## 14.3 역할 영역 B: View / 회원 / 마이페이지 / 관리자 화면

담당 범위:

- JSP 화면 구성
- 공통 header/footer 작성
- 로그인 화면
- 회원가입 화면
- session 기반 로그인 처리 보조
- 일반 회원 / 관리자 메뉴 분리
- 스터디룸/사물함 목록 화면
- 예약 시간 선택 화면
- 내 예약 현황 화면
- 예약 취소 요청 화면
- 관리자 예약 관리 화면
- 관리자 매출 통계 화면
- 최종보고서 화면 캡처 정리

주요 산출물:

- `login.jsp`
- `register.jsp`
- `study-room-list.jsp`
- `locker-list.jsp`
- `reservation-form.jsp`
- `reservation-complete.jsp`
- `my-reservations.jsp`
- `admin-dashboard.jsp`
- `admin-reservations.jsp`
- `admin-sales.jsp`

## 14.4 공동 담당

공동 작업:

- 요구사항 분석
- 기능정의서 작성
- 일정표 작성
- R&R 작성
- ERD 검토
- 코드 리뷰
- 통합 테스트
- 최종보고서 작성
- 발표자료 작성
- 발표 시연 준비

---

## 15. 테스트 계획

## 15.1 회원 테스트

| 테스트 | 기대 결과 |
|---|---|
| 정상 회원가입 | users 테이블에 회원 정보 저장 |
| 중복 아이디 회원가입 | 오류 메시지 출력 |
| 정상 로그인 | session에 userId, role 저장 |
| 잘못된 비밀번호 로그인 | 로그인 실패 메시지 출력 |
| 로그아웃 | session 종료 |

## 15.2 예약 테스트

| 테스트 | 기대 결과 |
|---|---|
| 예약 가능 시간 조회 | AVAILABLE slot 목록 출력 |
| 정상 예약 | reservations, reservation_slots, payments 저장 |
| 이미 예약된 시간 예약 | 예약 실패 메시지 출력 |
| 동일 시간 동시 예약 | 한 요청만 성공 |
| 예약 후 slot 상태 확인 | RESERVED로 변경 |

## 15.3 마이페이지 테스트

| 테스트 | 기대 결과 |
|---|---|
| 내 예약 조회 | 본인 예약만 출력 |
| 타인 예약 접근 | 접근 차단 |
| 취소 요청 | 예약 상태 CANCEL_REQUESTED 변경 |

## 15.4 관리자 테스트

| 테스트 | 기대 결과 |
|---|---|
| 관리자 예약 조회 | 전체 예약 출력 |
| 일반 회원 관리자 URL 접근 | 접근 차단 |
| 취소 승인 | 예약 CANCELLED, slot AVAILABLE 변경 |
| 매출 통계 조회 | 결제 금액 합계 출력 |

---

## 16. 개발 시 유의사항

## 16.1 과제 조건 준수

- Spring Framework를 사용하지 않는다.
- Spring Boot를 사용하지 않는다.
- JPA/Hibernate를 사용하지 않는다.
- Controller, Service, DAO, DTO 구조를 직접 구현한다.
- 모든 주요 요청은 `*.do` 패턴으로 처리한다.
- JSP는 `WEB-INF/views` 하위에 둔다.
- DB 연결은 DataSource를 통해 관리한다.

## 16.2 H2 사용 유의사항

- `jdbc:h2:mem`은 최종 시연용으로 사용하지 않는다.
- `jdbc:h2:file` 기반으로 DB 파일을 유지한다.
- MySQL Mode를 사용하되, H2와 MySQL의 동작 차이를 고려한다.
- SQL은 가능한 표준 SQL 중심으로 작성한다.
- 추후 MySQL/MariaDB로 전환할 수 있도록 DAO 구조를 분리한다.

## 16.3 트랜잭션 유의사항

- 예약 생성은 반드시 하나의 트랜잭션 안에서 처리한다.
- 예약 생성 중 하나라도 실패하면 rollback한다.
- Connection은 finally에서 반드시 close한다.
- SELECT FOR UPDATE로 slot을 잠근 뒤 상태를 확인한다.
- 예약 성공 후 slot 상태를 반드시 RESERVED로 변경한다.

## 16.4 JSP 유의사항

- JSP에서 DB 접근 코드를 작성하지 않는다.
- JSP는 출력만 담당한다.
- 비즈니스 로직은 Service에서 처리한다.
- SQL 실행은 DAO에서만 처리한다.
- EL/JSTL을 사용하여 화면을 구성한다.

## 16.5 권한 처리 유의사항

- 로그인하지 않은 사용자는 예약, 마이페이지에 접근할 수 없다.
- 일반 회원은 관리자 URL에 접근할 수 없다.
- 관리자는 관리자 페이지에 접근할 수 있다.
- session의 role 값을 기준으로 권한을 확인한다.

---

## 17. 최종 제출 산출물

최종 제출 시 다음 산출물을 준비한다.

- 프로젝트 소스코드
- `README.md`
- DB 스키마 파일 `schema.sql`
- 초기 데이터 파일 `data.sql`
- 기능정의서
- 일정표
- R&R
- 중간 수행 보고서
- 최종 보고서
- 발표자료
- 시연 시나리오
- 테스트 결과 캡처

---

## 18. README 요약용 문구

CampusReserve는 Java 21 LTS, Servlet/JSP, H2 Database를 기반으로 구현하는 스터디룸 및 사물함 예약 시스템이다.  
Spring Framework 없이 FrontController와 Command 패턴을 직접 구현하고, Service/DAO/DTO 계층 구조를 적용한다.  
예약 생성 시 JDBC 트랜잭션과 DB Lock을 사용하여 동일 시간대 중복 예약을 방지하며, 일반 회원과 관리자 권한을 분리하여 마이페이지 및 관리자 페이지를 제공한다.
