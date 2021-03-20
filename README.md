### 3.HATEOAS 와Self-Describtive Message 적용
#### API 인덱스 만들기

- 웹페이지 처음 접근할때 APi 진입점을 만든다.
- 각각의 리소스에 대한 루트가 나오길 바란다.(링크)
- 에러가 발생하면 전이 가능한 곳은 index 뿐.

##### linkTO
```java
add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
IndexController.class 의 index() 라는 메소드 로 링크를 만든다.
@GetMapping("/api")
public RepresentationModel index(){
}

"index": {
        "href": "http://localhost:8080/api"
}
```

##### 리팩토링
```java
//변경전
EntityModel<Errors> errorsModel = EntityModel.of(errors);
errorsModel.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
if(errors.hasErrors()){
    return ResponseEntity.badRequest().body(errorsModel);
}

//변경후
public class ErrorsResource extends EntityModel<Errors>{
    public ErrorsResource(Errors content , Link... links){
        super(content , links);
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}

private ResponseEntity<ErrorsResource> badRequest(Errors errors){
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
}
if(errors.hasErrors()){
    return badRequest(errors);
}

```