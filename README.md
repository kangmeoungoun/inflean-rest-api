### 3.HATEOAS 와Self-Describtive Message 적용
#### 스프링 REST Docs 각종 문서 조각 생성하기

- responseFields 모든 문서 전부 확인
- relaxedResponseFields 문서의 일부분만 확인
- responseFields 응답에 대한 것 다 적어주기 

```java
//responseFields 타입의 반환값들을 타입을 확인할때 
fieldWithPath("id").type(JsonFieldType.NUMBER).description("identifier of new event"),
fieldWithPath("name").type(JsonFieldType.STRING).description("name of new event"),
fieldWithPath("description").description("description of new evnet"),
        
//반환된 description 타입값이 있을수도 있고 없을수도 있을때
fieldWithPath("description").description("description of new evnet").optional()


//링크는 이미 문서로 만들어 졌기때문에 responseFields 다시 적어야 할떄 link to self 문서에 보이지 않는다.
fieldWithPath("_links.self.href").description("link to self").ignored(),

```