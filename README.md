### 5.REST API 보안 적용
#### 스프링 시큐리티 폼 인증 설정

##### AccountRepository 에서 Account 만들떄 passwordEncoder 해줘야한다.
```java

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(accountService)
            .passwordEncoder(passwordEncoder);
}
시큐리티 설정에 패스워드 인코더 사용한다고 했는데 AccountRepository 에서는 사용을 안해서
패스워드 매칭이 안된다.

```
```java
로그인시 DaoAuthenticationProvider
UserDetailsService.loadUserByUsername를 구현한
AccountService loadUserByUsername() 호출.



```
![image](https://user-images.githubusercontent.com/40969203/111906317-5244d380-8a93-11eb-99aa-b21ba2cd6a97.png)
