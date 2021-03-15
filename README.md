### 2.이벤트 생성 API 개발
#### 이벤트 API 테스트 클래스 생성


```java
@WebMvcTest 
//웹과 관련된 테스트 여서 슬라이싱 테스트 타고 부른다.
//모든 빈들은 등록하는게 아니라 웹과 관련된 것들만 등록
//근데 이자체를 단위테스트라고 보기 어렵다.
//MockMvc 웹서버를 뛰우지 않아서 조금 빠르다. 디스패치 서블릿을 만들어야 해서 단위테스트 보다 빠르진 않다.

mockMvc.perform(post("/api/events/"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(status().is(201)) //이렇게도 가능 
```

![image](https://user-images.githubusercontent.com/40969203/111149483-47d89480-85d0-11eb-863e-9382bb2137ee.png)
