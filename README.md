# ScheduleApplication
## 일정 관리 API 명세서

### 일정 등록 (Create Schedule)
| 진행현황 | 분류     | 기능         | Method | URL        | Request                                                                                                             | Response                                                                                                            | 상태코드        |
|---------|----------|--------------|--------|------------|---------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------|
| 완료    | schedule | 일정 등록    | POST   | /schedules | ```json { "title": "제목1", "content": "내용1", "username": "작성자1", "password": "3917" } ```                     | ```json { "id": 1, "title": "제목1", "content": "내용1", "username": "작성자1", "createdDate": "2025-02-04T11:38:00", "updatedDate": "2025-02-04T11:38:00" } ``` | 201(Created) 없음 |

### 전체 일정 조회 (Find All Schedules)
| 진행현황 | 분류     | 기능            | Method | URL        | Request Params                                                                                   | Response                                                                                                            | 상태코드        |
|---------|----------|-----------------|--------|------------|--------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------|
| 완료    | schedule | 전체 일정 조회  | GET    | /schedules | `updatedDate` (옵션): `2025-02-04` <br> `username` (옵션): `작성자1`                               | ```json [ { "id": 1, "title": "제목1", "content": "내용1", "username": "작성자1", "createdDate": "2025-02-04T11:38:00", "updatedDate": "2025-02-04T11:38:00" } ] ``` | 200(OK), 404(Not Found) |

### 선택 일정 조회 (Find Schedule by ID)
| 진행현황 | 분류     | 기능            | Method | URL               | Request       | Response                                                                                                            | 상태코드        |
|---------|----------|-----------------|--------|-------------------|--------------|---------------------------------------------------------------------------------------------------------------------|-----------------|
| 완료    | schedule | 선택 일정 조회  | GET    | /schedules/{id}   | 없음          | ```json { "id": 1, "title": "제목1", "content": "내용1", "username": "작성자1", "createdDate": "2025-02-04T11:38:00", "updatedDate": "2025-02-04T11:38:00" } ``` | 200(OK), 404(Not Found) |

### 선택 일정 수정 (Update Part Schedule)
| 진행현황 | 분류     | 기능            | Method | URL               | Request                                                                                                             | Response                                                                                                            | 상태코드        |
|---------|----------|-----------------|--------|-------------------|---------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------|
| 완료    | schedule | 선택 일정 수정  | PATCH  | /schedules/{id}   | ```json { "content": "수정된 내용", "username": "수정된 작성자", "password": "3917" } ```                           | ```json { "id": 1, "title": "제목1", "content": "수정된 내용", "username": "수정된 작성자", "createdDate": "2025-02-04T11:38:00", "updatedDate": "2025-02-04T11:45:00" } ``` | 200(OK), 400(Bad Request), 401(Unauthorized) |

### 선택 일정 삭제 (Delete Schedule)
| 진행현황 | 분류     | 기능            | Method | URL               | Request Params                                                                                   | Response                                                                                                            | 상태코드        |
|---------|----------|-----------------|--------|-------------------|--------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-----------------|
| 완료    | schedule | 선택 일정 삭제  | DELETE | /schedules/{id}   | `password`: `3917`                                                                              | 없음                                                                                                                | 200(OK), 401(Unauthorized) |
![image](https://github.com/user-attachments/assets/55696321-4d5f-4737-afa6-e7c21ad9dea5)
SQL쿼리 작성
1) Create
   	 
CREATE schedules users (
id INTEGER(30) NOT NULL,
password VARCHAR(20) NOT NULL,  
title VARCHAR(100) NOT NULL, 
content VARCHAR(100) NOT NULL, 
createdate DATETIME NOT NULL,
updateddate DATETIME NOT NULL,
username VARCHAR(20) NOT NULL
);    
2)Insert

INSERT INTO schedules (
"title": "LV0 과제 제출",
"content": "API명세서 작성하여 검사 받기",
"createdate": "now()",
"updateddate": "now()"
)
3)Select

SELECT *
FROM schedules
ORDER BY createdate DESC;
4)Select

SELECT *
FROM schedules
WHERE id='1';
5)update

UPDATE schedules
SET title='LV0 과제 검토' --> 할일,작성자명만 수정
WHERE title='LV0 과제 제출';
6)delete 

DELETE FROM users
WHERE id = 1;
