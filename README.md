# 📅 Schedule Project
- Spring Boot JPA를 사용하여 일정 게시판을 구현하는 기본적인 웹 프로젝트입니다.
- 개발기간 - 2024년 10월 07일 - 2024년 10월 17일

## ⚙️ 개발 환경
- **프로그래밍 언어**: Java 17
- **IDE**: IntelliJ IDEA
- **Database**: MySQL
- **운영 체제**: Windows

# 프로젝트 개요

스프링 Boot JPA를 사용하여 일정 게시판을 구현하는 기본적인 웹 프로젝트입니다.

## 개발 기간
2024년 10월 07일 - 2024년 10월 17일

## ⚙️ 개발 환경
- **프로그래밍 언어:** Java 17
- **IDE:** IntelliJ IDEA
- **Database:** MySQL
- **운영 체제:** Windows

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
![image](https://github.com/user-attachments/assets/0a540b22-5a15-40cb-94c2-16885e37c159)



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
