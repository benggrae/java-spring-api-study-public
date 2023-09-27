## 코드 리뷰

### EOL(End Of Line) 이슈

```yaml
.idea/**
.gradle/**
build/**
application.yaml
```

위와 같이 파일 가장 끝 부분에 공백을 추가하지 않으면, Github에서 코드를 볼 때 ⊖ 기호가 생긴다.
이러한 기호가 발생하는 이유는 `POSIX` 표준을 지키지 않았기 때문이다.

[IEEE 3.206 Line](https://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap03.html#tag_03_206)을 살펴보면 아래와 같은 내용이 적혀있는 것을 볼 수 있다.

> A sequence of zero or more non- <newline> characters plus a terminating <newline> character.

[IEEE 3.195 Incomplete Line](https://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap03.html#tag_03_206)에도 아래와 같은 내용이 있다.

> A sequence of one or more non- <newline> characters at the end of the file.

즉, 끝나지 않은 행은 파일 끝에 `non-newline` 문자가 하나 더 있어야한다는 것이다.
이러한 문제는 엔터 한 번이면 해결할 수 있지만, 표준에서는 `newline`이 아닌 문자를 넣으라고 한다.
그럼에도 새로운 라인만 추가하는 이유는 `git`에서 자동으로 `EOL` 값을 넣어주기 때문이다.

### 데이터 삭제 처리

실무에서는 실제 데이터를 삭제하는 경우는 거의 없다고 한다.
때문에 `useYN`과 같은 필드를 추가로 두어, 사용자가 삭제 처리를 할 경우, `false`의 값을 입혀 보이지 않게만 하는 용도로 사용한다.

실제로 잘못 삭제했을 경우를 대비해 이와 같이 조치하는 것이 바람직한 것 같다!

### DTO 반환

보통 `Controller`에서 프론트로 값을 반환하기 전에 DTO로 변환하는 습관이 있었다.

![image](https://github.com/Back-Mo/java-spring-api-study/assets/82663161/368a2a58-44cc-48cb-8953-6dfe877e647b)

하지만 위 사진과 같이 가능한 `Repository`에서 바로 DTO로 반환하는 것이 좋다고 한다.
자세한 내용은 [짱민님 블로그](https://leezzangmin.tistory.com/47)에서 확인할 수 있다.

간략하게 보면, `Entity`에는 수많은 필드가 존재한다. 하지만 실제로 프론트에 뿌려지는 데이터는 그 중 절반 정도가 될 것이다.
즉, 모든 정보를 불러와 `Entity`에 매핑하는 것은 불필요한 행위가 되는 것이다.

이와 같이 가능한 행위 처리 후에 필요한 내용만 보낼 수 있도록 DTO를 전달해주는 것이 더 좋은 것이다.

### 요청 실패에 대한 RFC7807 규약

사실 요청에 실패했을 때, 실패 코드와 메시지만 반환하면 된다고 생각했다.
[sungyoung님 블로그](https://sungyong.medium.com/rfc7807-restful-api-%EC%8B%A4%ED%8C%A8%EC%97%90-%EB%8C%80%ED%95%9C-%ED%91%9C%EC%A4%80-411a3c369795)를 보면 아래와 같은 규약이 있다는 것을 알 수 있다.

```json
HTTP/1.1 403 Forbidden
Content-Type: application/problem+json
Content-Language: en
        
{
    "type": "https://example.com/probs/out-of-credit",
    "title": "You do not have enough credit.",
    "detail": "Your current balance is 30, but that costs 50.",
    "instance": "/account/12345/msgs/abc",
    "balance": 30,
    "accounts": ["/account/12345",
                 "/account/67890"]
}
```

위와 같이 에러에 대해서 어떤 정보들을 반환해줘야하는지 규약 같은 것이 정해져있다.
다음 요구사항에서는 조금 더 고민해서 반환 정보를 추가해줘야할 것 같다!

### SoftAssertions

`assertThat`을 이용한 테스트가 여러 개 있을 경우, 테스트 실패할 경우 바로 중단이 된다.
하지만 `SoftAssertions`를 이용하면 모든 `assertions`를 실행한 후, 실패 내역에 대해서 확인할 수 있게 된다.

작은 단위부터 실패하는 테스트를 만들어보고, 성공하는 테스트로 넘어가는 방식으로 작성해보면 좋을 것 같다!

### 반환 타입의 와일드 카드

아래 코드와 같이 반환 타입을 정해주지 않고, `?`로 사용해서 반환했었다.

```java
public ResponseData.ApiResult<?> saveMenuApi(@RequestBody MenuDto dto){
    ...
}
```

하지만 `ApiResult<?>`에 들어가는 타입은 `Dto`가 명백하기 때문에 `ApiResult<MenuDto>` 이렇게 자료형을 제한하는 것이 올바르다고 한다.

### `record` 유효성 검증

최대한 간단하게 만들다보니 유효성 검증에 대해 잊고 있었다..

```java
public record MenuDto(Long id, String menuName) {

}
```

유효성 검증을 할 때 보통 각 필드에 하게 된다.
하지만 `record`의 경우 생성자와 같은 모습을 하고 있어 유효성 검증하는 방법이 조금은 다르다.

```java
public record MenuDto(
       Long id,
       @NotBlank(messgae ="메뉴 이름을 필수입니다.")
       String menuName
){
    
}
```

확실히 `record`를 사용하는 것보단 `class`를 이용해서 유효성 검증을 하는게 더 깔끔할 것 같다..!