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

##### Error 가  BindResult 상위 인터페이스라 에러를 써도 상관없지만  BindResult 에 필요한 메소드가 있을시
BindResult 를 사용한다.

##### Validator 등록방법
- 1.강의에서 처럼 EventValidator 빈으로 등록후  dto,errors 를 파라미터로 받는 메소드를 사용.
- 2.Validator 인터페이스를 구현한 클래스를 컨트롤러에서 @InitBinder 이용하여 사용. 
```java
 @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.addValidator(new EventValidator());
   }
```
