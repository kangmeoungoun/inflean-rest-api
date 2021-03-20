### 3.HATEOAS 와Self-Describtive Message 적용
#### 이벤트 목록 조회 API 구현

##### eventRepository.findAll(pageable) 로조회할시 이전페이지 다음페이지 에 대한 링크가 없다.
1. PageAble 처리
```java
pageable 은 있지만 관련 링크가 존재 하지 않는다.

```   
2. PagedResourcesAssembler 처리 스프링데이터jpa가 제공
```java
var pagedResources = assembler.toModel(page,e -> new EventResource(e));
pagedResources.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
//링크 정보를 만들어주고 각 이벤트마다 링크를 만들어준다.

해석
assembler.toModel(page,e -> new EventResource(e))
첫번째 파라미터 페이지
두번째 파라미터 Event 를 파라미터로 받고 RepresentationModel 를 상속받고있는 클래스 를 리턴하는
두개조건 모드 만족하는게 new EventResource
        
Page<Event> page = eventRepository.findAll(pageable);
public <R extends RepresentationModel<?>> PagedModel<R> toModel(Page<T> page, RepresentationModelAssembler<T, R> assembler) {
        return this.createModel(page, assembler, Optional.empty());
}

public interface RepresentationModelAssembler<T, D extends RepresentationModel<?>>{
    D toModel(T entity);
}


```