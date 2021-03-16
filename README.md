### 2.이벤트 생성 API 개발
#### 입력값 이외에 에러 발생

##### json문자열을 Object 로 만드는것을 deserialization
#####  Object 를 json문자열을 로 만드는것을 serialization 라고 한다.

##### 받을수 없는 값들을 넘길때
```java
테스트에서는 Id 값을 넘기고 있지만 EventDto 받는 쪽에서는 id 변수가 없어서 받을수 었는데
spring.jackson.deserialization.fail-on-unknown-properties=true
프로퍼티에 위 값을 주면 400에러 발생 시킨다 스프링 부트는 하지만 false 를 줄시 에러가 발생하지 않고 무시된다.
선택에 따라 달린다. 이런 것 같은경우는.

```

![image](https://user-images.githubusercontent.com/40969203/111311112-2bf0f380-86a1-11eb-884d-a6ddc87b1444.png)