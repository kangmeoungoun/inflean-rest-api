### 3.HATEOAS 와Self-Describtive Message 적용
#### 스프링 HATEOAS 소개

- REST application architecture  컴포넌트중 하나.
- 하이퍼 미디어를 사용하여 애플리케이션 서버에 정보를 동적으로 정보를 주고 받을수 있는 방법
- HATEOAS 목적은 REST 표현할때 보다 쉽게 제공해주는 라이브러리

##### EX)
```xml
GET /accounts/12345 HTTP/1.1
Accept : application/xml

<account_number>12345</account_number>
<balance>100.00</balance>
<link rel="deposit" href ="https://bank.example.com/accounts/12345/deposit">  
<link rel="withdraw" href ="https://bank.example.com/accounts/12345/withdraw"> 
<link rel="transfer" href ="https://bank.example.com/accounts/12345/transfer">
    
//현재 이 resource 릴레이션 어떠한 상호작용을 할수 있는지. 
//클라이언트는 이 url 만 보고 판단하기 때문에 서버에서 바껴도 상관이 없다.
<account_number>12345</account_number>
<balance>-25.00</balance>
<link rel="deposit" href ="https://bank.example.com/accounts/12345/deposit">
동일한 요청을 보냈지만 리소스의 상태를 보고 링크정보 가 바뀐다.
```

- 링크 
    - href : uri 설정
    - rel   : 현재 이 리소스와의 관계
        - self  : 자기자신
        - profile : 응답본문 문서 링크 
        - ....

