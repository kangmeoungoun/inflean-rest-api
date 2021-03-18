### 2.이벤트 생성 API 개발
#### 매개변수를 이용한 테스트

- 참고사항
```java
https://junit.org/junit5/docs/current/user-guide/
@MethodSurce 참고

@Test ->  @ParameterizedTest

@MethodSource("stringAndBooleanProvider")
static Stream<Arguments> stringIntAndBooleanProvider(){
        return Stream.of(
            Arguments.arguments(0,0,true),
            Arguments.arguments(100,0,false),
            Arguments.arguments(0,100,false)
        );
}
        Arguments 리턴타입 Object[]
        Stream.of 리턴은 제네릭 가변인자 여러개 받을수 있는
        Arguments.arguments()메소드값은 오브젝트 가변인자.

```

