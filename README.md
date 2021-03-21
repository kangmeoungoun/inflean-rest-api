### 5.REST API 보안 적용
#### 스프링 시큐리티 기본 설정

##### WebSecurity HttpSecurity 차이
```java
public void configure(WebSecurity web) throws Exception{
        web.ignoring().mvcMatchers("/docs/index.html");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
}

public void configure(HttpSecurity web) throws Exception{}

WebSecurity 걸면 SpringSecurity 필터 타지 않는다.
HttpSecurity 걸면 SpringSecurity 필터 내에서 걸러진다.
정적인것들은 처리할떄는 WebSecurity 걸러 주는게 좋다.
```
![image](https://user-images.githubusercontent.com/40969203/111905598-d09f7680-8a8f-11eb-9eeb-f8aa7456c797.png)


