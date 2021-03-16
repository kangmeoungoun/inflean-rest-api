### 2.이벤트 생성 API 개발
#### 입력값 제한하기

##### @SpringBootTest 이경우에는 MockMvc 사용할려면 
```java
@AutoConfigureMockMvc 예를 사용한다.
이렇게 하면 더이상 목킹이 적용되지 않아 실제 로 동작한다.


@SpringBootTest
@AutoConfigureMockMvc
이상태에서
@MockBean
EventRepository eventRepository; 이렇게 적용할시
실제 컨트롤러에서 목객체가 주입된다.

```
##### 주로 웹쪽관 관련된 테스트는 @SpringBootTest 하는게 좋다. 목킹해줘야 될것이 너무 많다.

##### 입력한 값 이외 무시하기
```java
Event event =Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, Month.MARCH,15,14,21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, Month.MARCH,16,14,21))
                .beginEventDateTime(LocalDateTime.of(2021, Month.MARCH,17,14,21))
                .endEventDateTime(LocalDateTime.of(2021, Month.MARCH,18,14,21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

값은 이런식으로 입력하였지만  id, free, offline, eventstatus 들은 계산해서 구해야 하므로
public ResponseEntity<Event> createEvent(@RequestBody EventDto eventDto){}
컨트롤러 단에서 Event 가 아닌 EventDto 로 받고 
EventDto 안에는 id, free, offline, eventstatus 존재하지 않게 되 Event 리턴시에 디폴트 값이 적용된다.

```

![image](https://user-images.githubusercontent.com/40969203/111308589-35c52780-869e-11eb-8b20-bd8dbf86b870.png)
