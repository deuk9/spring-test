# Spring boot 레이어별 테스트


## 1. controller 테스트
- mockMvc를 이용하여 테스트를 진행한다.
- service layer를 mocking 하여 진행한다. 

## 2. service 테스트
- repository 를 mock처리를 하지 않는다.
- `testcontainer` 를 이용하여 실제처럼 테스트를 진행한다.

## 3. repository 테스트
- JpaRepository 에 추가된 함수만 테스트를 진행한다.
- `testcontainer` 를 이용하여 실제 사용하는 DB 로 테스트를 진행한다.

## 4. 정리
- service, repository 레이어는 `testcontainer`를 이용하여 테스트
- controller 는 service 레이어를 mocking 하여 처리한다.

