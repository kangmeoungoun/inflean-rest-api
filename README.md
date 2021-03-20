### 3.HATEOAS 와Self-Describtive Message 적용
#### 테스트용 DB와 설정 분리하기

##### application.properties main/test 분리
1. application.properties test 에 추가 
    - main 에 있는 application.properties test application.properties 가 덮어쓴다.
    - 소스먼저 컴파일 되고 그다음에 테스트 컴파일 해서 소스해서 적용한것들이 다 날아간다. 그래서 중복설정할 값들이 많아진다.
    - 그래서 오버라이딩 하고 싶은것만 하기 위해 application.properties 파일 이름을 변경해준다.
    - 변경한대신 기본적으로 사용되지 않는다 직접 선언을 해주어야한다.
    - application-test.properties 로 변경후 테스트 클래스에 @ActiveProfiles("test") 추가
    
