### 2.이벤트 생성 API 개발
#### Bad Request 응답

##### Errors json 변환 할수 없는 이유
```java
오브젝트 맵퍼를 써서 변환하는데 그때 오브젝트는맵퍼는 BeanSerializer 를 사용한다.
Errors 는 자반 bean 스펙을 준수하고 있지 않아 serializer 안된다.

```
##### Errors 에 대한 Serializer 만들기
```java
- 필드 에러
errors.rejectValue("maxPrice","wrongValue","MaxPrice is Wrong"); 
- 글로벌 에러
errors.reject("wrongPrices","Values fo prices are wrong"); 

Errors에대한 Serializer 만들때는 필드에러, 글로벌 에러 를 둘다 맵핑해준다.

 
스프링 부트가 제공하는 오브젝트 맵퍼에 등록해야 한다 해당 클래스에 @JsonComponent 추가
오브젝트 맵퍼는 에러 Serializer 를 한다 Errors 라는 객체를 사용할때.
```