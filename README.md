### 5.REST API 보안 적용
#### 스프링 시큐리티 적용

- 스프링 시큐리티
    - 웹 시큐리티 : 웹 요청에다가 보안적용
    - 메소드 시큐리티 : 어떤 메소드가 호추됬을때 보안 적용(AOP 라고 생각)
    
요청을  시큐리티 인터셉터 가 받아서 이 요청을 인증 해야 되는지 안해야 되는지 확인
인증 해야 되면 인증 정보 확인(Security Context Holder) 없으면 로그인
AuthenticationManager(로그인 담당) 이 사용 하는 주요 인터페이스 UserDetailsService 
인증 요청 헤더에 인증,베이직 username,password 를 합쳐서 인코딩한 문자열을  UserDetailsService 를 사용하여
디비에 username password 를 읽어서 매칭 확인 passwordEncoder 로 확인 매칭 되면 Security Context Holder 에 저장

인증이 되면 권한 확인 User 의 roles 로 확인

##### 실행순서
```java
1.Account 계정을 만들어주고 DB 에 INSERT
        
String password = "goldapple";
        String username = "kes98202@naver.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN,AccountRole.USER))
                .build();
        this.accountRepository.save(account);
        
2.accountService 를 사용하여 UserDetailsService 변환 사실 여기는 안해줘도 상관 없을것 같다.accountService 이미  UserDetailsService를 구현 하고 있기때문에
        username 으로 UserDetails 를 만듦      

UserDetailsService userDetailsService = accountService;
UserDetails userDetails = userDetailsService.loadUserByUsername(username);

3.loadUserByUsername 실제 구현
Email 을 username 으로 생각 하고 찾는다. 없으면 UsernameNotFoundException 처리 
있으면 User 객체를 생성해주는데 이떄 User 는 UserDetails 의 구현체 이다. 
new User() 시 생성자의 순서는 name,password,roles 인데 
롤을 만들때 우리가 가지고 있는 Set 을 stream.map 을 통해 GrantedAuthority 의 구현체 new SimpleGrantedAuthority 생성 

public class AccountService implements UserDetailsService{
  private final AccountRepository accountRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    Account account = accountRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));


    return new User(account.getEmail() , account.getPassword() , authorities(account.getRoles()));
  }

  private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles){
    return roles.stream()
            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
            .collect(Collectors.toSet());
  }
}


```

