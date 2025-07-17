# 렌토(RENTO) ✨

<img src="https://github.com/user-attachments/assets/f9443d7a-24a5-4415-8f65-407cc9d82a19" width="500"/>

<br>

## 🎀 프로젝트 소개

🏷 **프로젝트 명 : 렌토(RENTO)**

🗓️ **프로젝트 기간 : 2025.04 ~ 2025.07 (10주)**

👥 **구성원 : 김승훈(팀장 👑), 이승현, 권동욱, 장병중, 박소윤**

---


### 🥰 서비스 구경 바로가기

**🎥 소개 영상 보기 : [Rento](https://youtu.be/W3fotJFDKAg)**


---

### ✅ 기획 배경
최근에는 차량을 소유하기보다는 **렌트카나 차량 공유 서비스를 필요할 때마다 이용하는 방식**이 보편화되고 있습니다. 이에 따라 **차량 사용 이력과 상태를 효율적으로 관리할 수 있는 관제 시스템의 필요성**이 커지고 있습니다.

특히 법인 차량의 경우, **여러 사용자가 한 차량을 공유함에 따라 운행 이력 관리가 복잡**해지고 있으며, 수기 작성 방식의 **운행일지 작성에도 많은 불편함**이 존재합니다.

우리는 이러한 문제를 해결하기 위해 **차량 관제 플렛폼**을 기획.개발하고자 합니다.

---

### ✅ 서비스 소개
**Rento**는 기업이나 기관이 보유한 차량을 보다 효율적으로 관리할 수 있도록 지원하는 **통합 차량 관제 플랫폼**입니다.

이 플랫폼은 **실시간 위치 추적, 운행 기록 관리, 예약 및 사용자 관리** 등 차량 운영에 필요한 주요 기능들을 **하나의 시스템에서 통합적으로 제공**합니다.

이 플랫폼을 통해 사용자는:

- **차량의 사용 이력(사용 시간, 이동 경로 등)을 조회**하여 **정상적인 운행 여부를 확인**할 수 있습니다
- **주행 기록을 기반으로 자동 운행일지를 생성**하여 효율적인 차량 관리를 할 수 있습니다.

---

### 👥 서비스 대상

-  법인 및 공공기관
-  렌터카 업체
-  차셰어링 서비스 운영 기업


<br>

## 💌 서비스 화면 및 기능 소개

### 로그인
![Image](https://github.com/user-attachments/assets/ea179539-6a79-45ad-bcc2-44ce60116e1c)

### 통계 대시보드
![Image](https://github.com/user-attachments/assets/1fb64b61-d856-4987-a2df-cd126b51ac8b)

### 부서 및 사용자 등록
![Image](https://github.com/user-attachments/assets/68447ee8-5900-4c4f-bd5c-9a1c6081b09b)

### 차량 등록
![Image](https://github.com/user-attachments/assets/f427be21-8a04-4204-ace6-42ea96a82565)

### 운행 예약 및 상세 보기
![Image](https://github.com/user-attachments/assets/1aa50ce7-0014-4e65-a777-d2f442e5ea5a)

### 실시간 관제
![Image](https://github.com/user-attachments/assets/bebc6b8e-a41d-4037-b5f7-b25b36203e43)
<br>

## 🛠 기술 스택

### 🌐 WEB
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white)

### 🗄 DB
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=flat&logo=rabbitmq&logoColor=white)

### ⚙ DevOps
![EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=flat&logo=amazonaws&logoColor=white)
![ALB](https://img.shields.io/badge/AWS%20ALB-FF9900?style=flat&logo=amazonaws&logoColor=white)
![RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=flat&logo=amazonaws&logoColor=white)
![ECR](https://img.shields.io/badge/AWS%20ECR-FF9900?style=flat&logo=amazonaws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat&logo=githubactions&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat&logo=firebase&logoColor=black)

### 💻 Tools
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ-000000?style=flat&logo=intellijidea&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)

<br>

## 🗂 프로젝트 구조 (멀티 모듈)
<details>
  <summary>프로젝트 구조를 보시려면 클릭해주세요!</summary>

  ```markdown
├── build
│   └── reports
│       └── problems
├── common
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── kbe5
│                       └── common
│                           ├── annotation
│                           ├── apiresponse
│                           ├── exception
│                           ├── response
│                           │   └── api
│                           └── util
├── domain
│   ├── build
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── kbe5
│                       └── domain
│                           ├── company
│                           │   ├── dto
│                           │   ├── entity
│                           │   └── service
│                           ├── cycleinfosummary
│                           │   ├── dto
│                           │   ├── entity
│                           │   └── service
│                           ├── department
│                           │   ├── dto
│                           │   ├── entity
│                           │   └── service
│                           ├── device
│                           │   ├── dto
│                           │   ├── entity
│                           │   ├── enums
│                           │   └── service
│                           ├── drive
│                           │   ├── dto
│                           │   ├── entity
│                           │   └── service
│                           ├── event
│                           │   ├── dto
│                           │   ├── entity
│                           │   ├── enums
│                           │   ├── handler
│                           │   └── service
│                           ├── exception
│                           ├── firebase
│                           │   ├── dto
│                           │   └── service
│                           ├── geofence
│                           │   ├── dto
│                           │   ├── entity
│                           │   ├── enums
│                           │   └── service
│                           ├── manager
│                           │   ├── dto
│                           │   ├── entity
│                           │   ├── enums
│                           │   └── service
│                           ├── member
│                           │   ├── dto
│                           │   ├── entity
│                           │   └── service
│                           ├── statistics
│                           │   ├── dto
│                           │   ├── entity
│                           │   └── service
│                           ├── stream
│                           │   └── service
│                           │       └── dto
│                           └── vehicle
│                               ├── dto
│                               ├── entity
│                               └── service
├── infra
│   ├── build
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── kbe5
│           │           └── infra
│           │               ├── infrastructure
│           │               │   ├── company
│           │               │   │   └── repository
│           │               │   ├── cycleDataSummary
│           │               │   │   └── repository
│           │               │   ├── department
│           │               │   │   └── repository
│           │               │   ├── device
│           │               │   │   └── repository
│           │               │   ├── drive
│           │               │   │   └── repository
│           │               │   ├── event
│           │               │   │   └── repository
│           │               │   ├── firebase
│           │               │   │   ├── config
│           │               │   │   └── service
│           │               │   ├── geofence
│           │               │   │   └── repository
│           │               │   ├── manager
│           │               │   │   └── repository
│           │               │   ├── member
│           │               │   │   └── repository
│           │               │   ├── statistics
│           │               │   │   └── repository
│           │               │   └── vehicle
│           │               │       └── repository
│           │               ├── jwt
│           │               │   ├── controller
│           │               │   ├── dto
│           │               │   │   ├── request
│           │               │   │   └── response
│           │               │   └── util
│           │               ├── rabbitmq
│           │               │   └── config
│           │               ├── redis
│           │               │   └── config
│           │               └── security
│           │                   ├── config
│           │                   ├── details
│           │                   ├── dto
│           │                   ├── filter
│           │                   └── util
│           └── resources
│               └── firebase
├── rento-api
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── kbe5
│           │           └── api
│           │               ├── config
│           │               └── domain
│           │                   ├── company
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── cycleDatasummary
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── department
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── device
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── drive
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── geofence
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── manager
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── member
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── statistics
│           │                   │   ├── controller
│           │                   │   ├── dto
│           │                   │   └── mapper
│           │                   ├── stream
│           │                   │   └── controller
│           │                   └── vehicle
│           │                       ├── controller
│           │                       ├── dto
│           │                       └── mapper
│           └── resources
│              └── firebase
├── rento-pub
│   ├── build
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── kbe5
│           │           └── pub
│           │               ├── amqp
│           │               ├── controller
│           │               ├── dto
│           │               │   ├── request
│           │               │   │   ├── cycleinfo
│           │               │   │   ├── geofence
│           │               │   │   └── onoff
│           │               │   └── response
│           │               └── mapper
│           └── resources
└── rento-sub
    └── src
        └── main
            ├── java
            │   └── com
            │       └── kbe5
            │           └── sub
            │               └── amqp
            └── resources

```

</details>



<br>

## 📜 프로젝트 산출물

### 시스템 아키텍쳐
![Architecture](https://github.com/user-attachments/assets/ff986472-b901-45cd-a7bc-b202e9780eda)

---

### 🎨 ERD

![ERD](https://github.com/user-attachments/assets/e1a852c8-15c0-4d5f-a80b-1430ac10c1da)

---

### 📄 API 명세서

**부서**
![Department](https://github.com/user-attachments/assets/0173570c-8b9a-46c5-997b-be9b3e75a0f6)

**매니저**
![Manager](https://github.com/user-attachments/assets/5ed3cda7-5d23-4938-8713-ffe6fbf7c187)

**회원**
![Member](https://github.com/user-attachments/assets/8bb6b9bc-fc85-4e1d-90af-faebf6786caf)

**회사**
![Company](https://github.com/user-attachments/assets/b1e603a6-772a-45be-93ce-efac18930d86)

**운행**
![Drive](https://github.com/user-attachments/assets/6f43a1f9-bf66-4859-a3d7-21197e0dbf84)

**차량**
![Vehicle](https://github.com/user-attachments/assets/13489771-ca17-42e2-b84a-7b7d1d1d666e)

**실시간 관제**
![Stream](https://github.com/user-attachments/assets/749ff668-d92a-47ff-9ae4-5848f0114b73)
<br>

## 💙 팀원 소개
| ![](https://avatars.githubusercontent.com/u/125844213?v=4) | ![](https://avatars.githubusercontent.com/u/67327887?v=4) | ![](https://avatars.githubusercontent.com/u/97105216?v=4) | ![](https://avatars.githubusercontent.com/u/110446078?v=4) | ![](https://avatars.githubusercontent.com/u/61807816?v=4) |
|:--:|:--:|:--:|:--:|:--:|
| [김승훈](https://github.com/SeungHun333) | [이승현](https://github.com/tmdgus717) | [권동욱](https://github.com/ugiuk00) | [장병중](https://github.com/JangBJ) | [박소윤](https://github.com/ehouse16) |
| 팀장  <br> 회원가입, 로그인 | 이벤트 저장 | 멀티모듈 | 차량 <br> 운행 예약 및 관리 <br> 실시간 관제 | 사용자 및 부서<br>FCM기반 알림발송<br>월별 통계 |
