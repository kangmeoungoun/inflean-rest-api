### 2.이벤트 생성 API 개발
#### 이벤트 Repository

##### EventRepository 를 사용해서 처음 테스트 할떄 WebMvcTest는 슬라이싱 테스트라 서 빈 주입을 못받는다.
```java
@MockBean
    EventRepository eventRepository;

이렇게 해도 테스트가 깨지는데 MockBean 으로 주입받응 에들은 전부 null 반환
그래서 stubbing해줘야 한다.
event.setId(10);
when(eventRepository.save(event)).thenReturn(event);

```
##### 리팩토링
```java

andExpect(header().exists("Location")
-> andExpect(header().exists(HttpHeaders.LOCATION)) 
TypeSafe 하게 수정.
```


##### Mockito에서 when()
```java

Event event =Event.builder()
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
        .build();
        event.setId(10);
        
        when(eventRepository.save(event)).thenReturn(event);

public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event newEvent = eventRepository.save(event);
        URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        event.setId(10);
        return ResponseEntity.created(createUri).body(newEvent);
    }


when(eventRepository.save(event)).thenReturn(event); //목킹한것
Event newEvent = eventRepository.save(event);        //실제 사용할떄
//이때 서로 when(eventRepository.save(event)) eventRepository.save(event) 예의 이벤트의객체가 같아야 
//when 이 동작한다.
//그 때 같은걸 알수 있게 커스텀하게 해주는것이 equals() 함수.
//중요한것은 전달 파라미터의 객체가 같아야 when 이 동작한다.
```
![image](https://user-images.githubusercontent.com/40969203/111160261-46619900-85dd-11eb-95d3-d926f9a6382a.png)

