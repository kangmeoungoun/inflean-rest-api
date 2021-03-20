### 3.HATEOAS 와Self-Describtive Message 적용
#### 스프링 HATEOAS 적용

```java 
@JsonUnwrapped
Event event

//이벤트 랩핑 지우고 싶을때 사용.
```
```json
 event:{
  "id": 1,
  "name": "Spring",
  "description": "REST API Development with Spring",
  "beginEnrollmentDateTime": "2021-03-15T14:21:00"
}
"id": 1,
"name": "Spring",
"description": "REST API Development with Spring",
"beginEnrollmentDateTime": "2021-03-15T14:21:00"
```