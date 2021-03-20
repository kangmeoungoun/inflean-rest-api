### 3.HATEOAS 와Self-Describtive Message 적용
#### 스프링 REST Docs 문서 빌드

#### index.html 어떻게 생성헀는지.
1. asciidoctor-maven-plugin 인으로 html 생성
2. package 에 asciidoctor-maven-plugin 이제공하는 process-asciidoc 실행
3. process-asciidoc 은 기본적으로 src/main/asciidoc 에 있는 adoc 문서를 html 로 만들어준다.
4. static/docs 에 파일 옮겨준 에는 maven-resources-plugin 가 해준다.
5. package 에 maven-resources-plugin 이제공하는 opy-resources 실행

[REST API Notes Guide 링크](https://github.com/spring-projects/spring-restdocs/blob/v1.2.6.RELEASE/samples/rest-notes-spring-hateoas/src/docs/asciidoc/api-guide.adoc)

