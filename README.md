### 2.이벤트 생성 API 개발
#### BadRequest 처리

##### 스프링부트 버전 2.3.0에서  starter web에 디펜던시로 spring-boot-starter-validation 제외
```xml

<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.1.Final</version>
</dependency>
validation-api -> spring-boot-starter-validation 를 사용
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

멋도 모르고 인텔리제이에서 그냥 add maven 을 해버러셔 위에걸 들고 와버렸다.
```