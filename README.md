# Order_system
---
### 프로젝트 설명
- 간단한 상품 주문 API 서버 제작 개인 프로젝트입니다.
- 여러가지 기술을 적용하여 성장하기 위해 제작한 프로젝트입니다.
- 불필요한 코드를 작성하지 않기 위해 노력했습니다.

### Dependency
- Spring Web
- JPA
- Spring Security
- JWT
- STOMP
- MySQL Driver
- Validation
- Swagger
- Redis
- AWS S3
- Docker, Docker-compose
- Flyway
- JUnit5 Test

### Git Convention
- feat: 기능 추가
- fix: 버그 수정
- refactor: 리팩토링, 기능은 그대로 두고 코드를 수정
- style: formatting, 세미콜론 추가 / 코드 변경은 없음
- chore: 라이브러리 설치, 빌드 작업 업데이트
- docs: 문서 변경, 주석 추가 삭제

### API
- Auth: 회원가입 및 Spring Security + JWT를 이용한 로그인 및 Redis를 이용하여 Refresh Token 관리
- Member: 유저 정보 관련 CRUD
- Order: 주문 관련 CRUD
- OrderItem: 주문한 상품 관련 CRUD
- Item: 모든 상품 조회
- chatRoom: 채팅방 관련 CRUD
- chatMessage: STOMP를 이용한 채팅
