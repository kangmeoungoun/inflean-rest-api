### 2.이벤트 생성 API 개발
#### 201 응답 받기

#### 테스트시 응답코드 한글 깨짐 현상 해결방법
    - body 응답값 : location":"ê°ë¨ì­ D2 ì¤íí í©í ë¦¬"
```java
//1
@Autowired
WebApplicationContext ctx;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8"))
                .alwaysDo(print())
                .build();
    }
//2
    mockMvc.perform(post("/api/events/")
    .contentType(MediaType.APPLICATION_JSON)
    .characterEncoding("UTF-8")
```




#### 헤더 정보에 location 집어넣기
```java
MockHttpServletResponse:
        Status = 201
        Error message = null
        Headers = [Location:"http://localhost/api/events/%257Bid%257D", Content-Type:"application/hal+json"]

        URI createUri = linkTo(EventController.class).slash("{id}").toUri();
        event.setId(10);
        return ResponseEntity.created(createUri).body(event);
```
- http://localhost/api/events/{id} 실제 id 의 값은 본문에 포함되어 있어서 클라이언트에서 교체해서 사용.
  
![image](https://user-images.githubusercontent.com/40969203/111154218-51fd9180-85d6-11eb-9f5c-776dd07a4adf.png)

