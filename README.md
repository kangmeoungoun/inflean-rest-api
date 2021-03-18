### 2.이벤트 생성 API 개발
#### 비즈니스 로직 활용

##### 심플 if else
```java
if(this.basePrice == 0 && this.maxPrice==0){
     this.free=true;
}else{
     this.free=false
}
this.free= this.basePrice == 0 && this.maxPrice == 0;
this.free= this.basePrice == 0 && this.maxPrice == 0 ? true:false;

```