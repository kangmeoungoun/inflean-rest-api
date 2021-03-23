### 5.REST API 보안 적용
#### 현재 사용자 조회

##### 인증된사용자 정보
```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
authentication.getPrincipal();
이때 getPrincipal() 은 UserDetailsService 구현체의 loadUserByUsername() 의 리턴값이다
이떄 리턴값은 User 을 상속받는 클래스또는 User 를 리턴한다.


public ResponseEntity getEvent(@PathVariable Integer id,
                                @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account") Account currentUser){}
메소드 파라미터에 위처럼 작성해주면 loadUserByUsername() 의 리턴값 에서 인증이 됬으면 getAccount()를 해줘 Accout 를 리턴
인증되지 않은 사용자는 loadUserByUsername() 의 리턴값 이 들어오지않고 "anonymousUser" 라는 문자열이 들어온다.
그래서 el표현식으로 3항연산자로 null 처리

```
FORBIDDEN(403) : 인증은 됐으나 해당 리소스를 볼 권한이 없으면 
UNAUTHORIZED(401) : 인증이 필요한 리소스에 접근할 때 인증이 안되어 있는 유저가 접근한경우
