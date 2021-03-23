### 5.REST API 보안 적용
#### 출력값 제한하기

##### 이벤트 생성시 Responsebody
```json
"manager": {
		"id": 3,
		"email": "admin@email.com",
		"password": "{bcrypt}$2a$10$0DFX4vbXmsFiyUghBtnxVO7fF1o4cx0W6v8FaOhI7pQXxYm6mhogK",
		"roles": [
			"USER",
			"ADMIN"
		]
	},

```
email, password,roles 는 보여주고 싶지 않을때
Account 에서  해당 컬럼에 @JsonIgnore 를 하면 되지만 그럴경우 모든 응답에 이 정보들이 빠지기 때문에
어떨때는 email 을 응답에 사용할때도 있기때문에 이런식으로 하면 안될것같다.
```java
public class AccountSerializer extends JsonSerializer<Account>{
    @Override
    public void serialize(Account account , JsonGenerator gen , SerializerProvider serializerProvider) throws IOException{
        gen.writeStartObject();
        gen.writeNumberField("id",account.getId());
        gen.writeEndObject();
    }
}

@JsonComponent 사용하지 않은 이유는 모든 응답에 다 적용하지 않기위해

@JsonSerialize(using = AccountSerializer.class)
private Account manager;
Event 에서 Account 에 이렇게 적용 해준다.
```