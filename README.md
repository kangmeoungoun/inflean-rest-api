### 2.이벤트 생성 API 개발
#### 이벤트 Repository

```java
EventRepository 를 사용해서 처음 테스트 할떄 WebMvcTest는 슬라이싱 테스트라 서 빈 주입을 못받는다.
@MockBean
    EventRepository eventRepository;

이렇게 해도 테스트가 깨지는데 MockBean 으로 주입받응 에들은 전부 null 반환
그래서 stubbing해줘야 한다.
event.setId(10);
when(eventRepository.save(event)).thenReturn(event);

```
```java

andExpect(header().exists("Location")
-> andExpect(header().exists(HttpHeaders.LOCATION)) 
TypeSafe 하게 수정.
```

![image](https://user-images.githubusercontent.com/40969203/111160261-46619900-85dd-11eb-95d3-d926f9a6382a.png)
