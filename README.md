# 📅 Schedule Project
- Spring JPA, Spring Security를 사용하여 일정 게시판을 구현하는 기본적인 웹 프로젝트입니다.
- 개발기간 - 2024년 10월 25일 - 2024년 10월 31일

## ⚙️ 개발 환경
- **프로그래밍 언어**: Java 17
- **IDE**: IntelliJ IDEA
- **Database**: MySQL
- **ORM**: JPA
- **Security**: JWT Token , Spring Security
## 📌 주요 기능

### 회원가입 및 로그인
- 사용자는 회원가입 후 로그인해야만 일정 및 댓글 CRUD 기능을 사용할 수 있습니다.

### 게시글 관리
- 로그인한 회원은 게시글 등록 및 조회가 가능합니다.
- 게시글의 수정 및 삭제는 관리자 권한을 가진 사람만 가능합니다.

### 댓글 관리
- 로그인한 회원은 댓글 등록, 조회, 수정 및 삭제가 가능합니다.

### 예외 처리 및 유효성 검증
- 다양한 예외처리를 통해 사용자가 입력한 데이터의 유효성을 검증합니다.
- 입력 데이터에 대한 조건을 설정하여 잘못된 데이터가 저장되지 않도록 합니다.

### 권한 관리
- 권한이 없는 사용자가 접근할 경우, "권한 없음" 메시지가 반환됩니다.

### 토큰 검증
- 만료된 토큰이나 토큰이 없는 경우에 대한 메시지를 반환합니다.

<br>

## 🔗 API 설계

## User API
| 기능     | Method | API Path                   | Request                                          | Response                                         | RequestHeader          | 상태코드          |
|----------|--------|----------------------------|--------------------------------------------------|--------------------------------------------------|------------------------|-------------------|
| 유저 등록  | POST   | `/api/users/signup`        | ```json { "username": "유저이름", "password": "비밀번호", "email": "이메일" } ``` | ```json { "id": 1, "username": "유저이름", "email": "이메일" } ``` | N/A                    | 201 Created      |
| 유저 로그인  | POST   | `/api/users/login`         | ```json { "username": "유저이름", "password": "비밀번호" } ``` | ```json { "token": "JWT 토큰" } ```             | N/A                    | 200 OK          |
| 유저 조회  | GET    | `/api/users/{userId}`      | N/A                                              | ```json { "id": 1, "username": "유저이름", "email": "이메일" } ``` | Authorization: Bearer {token} | 200 OK          |
| 유저 수정  | PUT    | `/api/users/{userId}`      | ```json { "username": "수정된 유저이름", "email": "수정된 이메일" } ``` | ```json { "id": 1, "username": "수정된 유저이름", "email": "수정된 이메일" } ``` | Authorization: Bearer {token} | 200 OK          |
| 유저 삭제  | DELETE | `/api/users/{userId}`      | N/A                                              | N/A                                              | Authorization: Bearer {token} | 204 No Content   |

## Schedule API
| 기능     | Method | API Path                       | Request                                   | Response                                         | RequestHeader          | 상태코드          |
|----------|--------|--------------------------------|-------------------------------------------|--------------------------------------------------|------------------------|-------------------|
| 일정 등록  | POST   | `/api/schedules`               | ```json { "title": "일정 제목", "content": "일정 내용" } ``` | ```json { "id": 1, "title": "일정 제목", "content": "일정 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1 } ``` | Authorization: Bearer {token} | 201 Created      |
| 일정 전체 조회 (페이징 추가) | GET    | `/api/schedules`               | N/A                                       | ```json { "content": [ { "id": 1, "title": "일정 제목", "content": "일정 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1 }, ... ], "totalPages": 5, "totalElements": 50 } ``` | Authorization: Bearer {token} | 200 OK          |
| 일정 단건 조회 | GET    | `/api/schedules/{scheduleId}` | N/A                                       | ```json { "id": 1, "title": "일정 제목", "content": "일정 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1, "comments": [ { "id": 1, "content": "댓글 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1 }, ... ] } ``` | Authorization: Bearer {token} | 200 OK          |
| 일정 수정  | PUT    | `/api/schedules/{scheduleId}` | ```json { "title": "수정된 일정 제목", "content": "수정된 일정 내용" } ``` | ```json { "id": 1, "title": "수정된 일정 제목", "content": "수정된 일정 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1 } ``` | Authorization: Bearer {token} | 200 OK          |
| 일정 삭제  | DELETE | `/api/schedules/{scheduleId}` | N/A                                       | N/A                                              | Authorization: Bearer {token} | 204 No Content   |

## Comment API
| 기능     | Method | API Path                        | Request                                   | Response                                         | RequestHeader          | 상태코드          |
|----------|--------|---------------------------------|-------------------------------------------|--------------------------------------------------|------------------------|-------------------|
| 댓글 등록  | POST   | `/api/schedules/{scheduleId}/comments` | ```json { "content": "댓글 내용" } ``` | ```json { "id": 1, "content": "댓글 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1, "scheduleId": 1 } ``` | Authorization: Bearer {token} | 201 Created      |
| 댓글 조회  | GET    | `/api/schedules/{scheduleId}/comments` | N/A                                       | ```json [ { "id": 1, "content": "댓글 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1, "scheduleId": 1 }, ... ] ``` | Authorization: Bearer {token} | 200 OK          |
| 댓글 수정  | PUT    | `/api/comments/{commentId}`     | ```json { "content": "수정된 댓글 내용" } ``` | ```json { "id": 1, "content": "수정된 댓글 내용", "createdAt": "2024-10-31T12:34:56", "updatedAt": "2024-10-31T12:34:56", "userId": 1, "scheduleId": 1 } ``` | Authorization: Bearer {token} | 200 OK          |
| 댓글 삭제  | DELETE | `/api/comments/{commentId}`     | N/A                                       | N/A                                              | Authorization: Bearer {token} | 204 No Content   |

<br>

## 📊  ERD
![image](https://github.com/user-attachments/assets/46133b00-03b9-4779-92f3-06565104b1d5)
<br>

----

<details>
<summary>Spring JPA 활용 Schedule Project README.md </summary>
<div markdown="1">

# 📅 Schedule Project
- Spring JPA를 사용하여 일정 게시판을 구현하는 기본적인 웹 프로젝트입니다.
- 개발기간 - 2024년 10월 07일 - 2024년 10월 17일

## ⚙️ 개발 환경
- **프로그래밍 언어**: Java 17
- **IDE**: IntelliJ IDEA
- **Database**: MySQL
- **운영 체제**: Windows


## 📌 주요 기능

### 일정 등록
- 사용자가 새로운 일정을 등록할 수 있습니다.

### 일정 목록 조회
- 등록된 모든 일정을 조회할 수 있습니다.
- 페이징 기능을 통해 일정 목록을 쉽게 관리할 수 있습니다.

### 일정 상세 조회
- 사용자가 선택한 일정의 상세 정보를 확인할 수 있습니다.
- 일정에 작성된 댓글 정보를 확인할 수 있습니다.

### 일정 수정
- 사용자는 일정을 수정할 수 있습니다.

### 일정 삭제
- 사용자는 등록된 일정을 삭제할 수 있으며, 이때 댓글도 함께 삭제됩니다.

### 댓글 등록 및 관리
- 사용자는 특정 일정에 댓글을 남길 수 있습니다.
- 댓글의 내용, 작성일, 수정일, 작성 유저명 등의 정보를 포함하여 CRUD 기능을 제공합니다.

### 유저 관리
- 유저 정보를 등록, 조회, 수정, 삭제할 수 있는 기능을 제공합니다.
- 유저는 고유 식별자 필드를 통해 관리되며, 유저와 일정 간의 관계를 설정하여 여러 사용자가 일정을 함께 관리할 수 있도록 합니다.

### 예외 처리 및 유효성 검증
- 다양한 예외처리를 통해 사용자가 입력한 데이터의 유효성을 검증합니다.
- 입력 데이터에 대한 조건을 설정하여 잘못된 데이터가 저장되지 않도록 합니다.



## 🔗 API 설계
![image](https://github.com/user-attachments/assets/5b162967-562c-4258-800f-49155c0b8f0a)
![image](https://github.com/user-attachments/assets/ead7850a-0268-4d32-8180-7afdbc07a039)
![image](https://github.com/user-attachments/assets/62529983-b6f5-4796-aa63-6c2d2452f67d)

## 📊  ERD
![image](https://github.com/user-attachments/assets/9c5f67e9-c5b5-4967-852c-f53e157a3194)


## 📁 파일 구조
```
my-spring-boot-app
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── schedule
│   │   │               ├── ScheduletApplication.java
│   │   │               ├── controller
│   │   │               │   └── CommentController.java
│   │   │               │   └── ScheduleController.java
│   │   │               │   └── UserController.java
│   │   │               ├── dto
│   │   │               │   └── CommentResponseDto.java
│   │   │               │   └── CreateCommentRequestDto.java
│   │   │               │   └── CreateScheduleRequestDto.java
│   │   │               │   └── CreateUserRequestDto.java
│   │   │               │   └── ScheduleResponseDto.java
│   │   │               │   └── UpdateCommentRequestDto.java
│   │   │               │   └── UpdateScheduleRequestDto.java
│   │   │               │   └── UpdateUserRequestDto.java
│   │   │               │   └── UserResponseDto.java
│   │   │               ├── entity
│   │   │               │   └── Comment.java
│   │   │               │   └── Schedule.java
│   │   │               │   └── User.java
│   │   │               ├── service
│   │   │               │   └── CommentService.java
│   │   │               │   └── ScheduleService.java
│   │   │               │   └── UserService.java
│   │   │               └── repository
│   │   │                   └── CommentRepository.java
│   │   │                   └── ScheduleRepository.java
│   │   │                   └── UserRepository.java
│   │   │    
│   ├── test
├── .gitignore
├── bulid.gradle
├── gradlew
├── gradlew.bat
├── schedule.sql
└── README.md
```
</div>
</details>
