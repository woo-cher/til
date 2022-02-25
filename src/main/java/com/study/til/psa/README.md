> **PSA ( Portable Service Abstraction )**
> 
> - 모듈이 특정 환경에 제약 받지 않고 항상 일관된 서비스를 제공할 수 있도록 추상화 한 것

</br>
우리가 사용하는 Spring 의 주요 기능들은 대부분 PSA 에 기반하여 만들어졌다.

예를 먼저 들자면, `@Controller` 와 같은 어노테이션만 붙여주면 우리는 서블릿에 기반한 핸들러를 구현할 수 있게 된다. 
만약, 구동 서버가 `Tomcat` 이 아닌 `jetty` 나 기타 서버를 이용한다 하더라도 코드의 큰 수정 없이 동일한 기능을 제공하는 것이다.

또 다른 예로는 `Spring Transaction`이다. 흔히 사용하는 `jdbc` 와 `jpa` 들은 `AbstractPlatformTransactionManager` 를 상속 받아서 Transaction 을 사용하기 위한 행위 들을 각 형태에 맞게 정의되어 있다.

그렇기 때문에 `jpa` 를 쓰든, `jdbc` 를 쓰던지 간에 동일한 @Transaction 어노테이션만 써주면 주입된 객체에 따라서 정의해둔 대로 실행이 될 것이고,

결과적으로는 동일한 트랜잭션 서비스를 제공해주는 것이다.

```java
💡 Spring 에서 찾아볼 수 있는 PSA

1. Web mvc
2. Transaction Manager
3. Database connection
4. Cache Manager
```

# ✅ Abstraction

`추상화`를 한다는 건 모듈이 수행할 공통적인 행위를 `선언`만 해두고, 이 행위에 대한 정의는 구현체에서 하는 것이다.

추상화를 간단히 사용한 예제를 생각해보았다. 나는 두 친구가 **(객체)** 소개 **(행위)** 를 한다고 가정했다. 이에 따라, 소개 행위를 담당할 `상위 계층 인터페이스`를 만들었다.

```java
interface Introduction {
    String doIntroduction();
}
```

두 객체 Foo 와 Baz가 각각 소개를 한다고 해보자.

그렇다면 아래와 같이 소개 행위를 정의할 클래스를 만들고, 상위 계층을 상속 받아 구현할 것이다.

```java
public class FooIntroductionService implements Introduction {

    @Override
    public String doIntroduction() {
        return "Hello, I'm Foo";
    }
}

public class BazIntroductionService implements Introduction {

    @Override
    public String doIntroduction() {
        return "Hello, I'm Baz";
    }
}
```

이걸 토대로 `추상화 계층`에서 메소드를 실행시켜보자.

```java
@Slf4j
public class PsaTests {
    private Introduction introduction; // 추상 클래스 계층

    @Test
    public void introduction() {
        introduction = new BazIntroductionService();
        log.debug("Who is ? {}", introduction.doIntroduction());

        introduction = new FooIntroductionService();
        log.debug("Who is ? {}", introduction.doIntroduction());
    }
}

(실행 결과)

16:41:13.083 [main] DEBUG com.study.til.psa.PsaTests - Who is ? Hello, I'm Baz
16:41:13.086 [main] DEBUG com.study.til.psa.PsaTests - Who is ? Hello, I'm Foo
```

추상화를 통해 `소개` 를 할 것이라는 인터페이스를 만들었다. 그리고, 실제로 소개할 친구들이 해당 인터페이스를 상속 받아 `소개` 행위를 정의했다.

실제로 메소드 콜을 할 때에는, 인터페이스 계층의 객체를 통해서 함수 호출을 했다.

물론, 위 코드에서는 개발자가 직접 DI 를 했다. 상위 객체에 실질적으로 구현체를 주입해주었기 때문에, `doIntroduction()` 했을 때 객체에 따라 다른 소개가 실행되었다.

Spring 에서 사용하는 psa 도 같은 맥락인 것 같다. 예를 들어, `@Transactional`에 대해 생각해보면 트랜잭션 로직이 공통적으로 수행해야 하는 행위를 선언해두고, `jdbc` 일 때, `jpa` 일 때, `hibernate` 일 때를 각각 상속 받아서 행위에 대해서 정의되어있다.

실제 코드를 살펴보면 아래와 같다.

```java
// 공통적으로 수행할 행위에 대한 선언 (추상화 계층)
public interface PlatformTransactionManager extends TransactionManager {
    TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;

    void commit(TransactionStatus status) throws TransactionException;

    void rollback(TransactionStatus status) throws TransactionException;
}
```

<br>
그리고, 해당 추상화 계층을 누가 상속 받았는지 살펴보면 `Datasource` 부터 `jdbc`, `jpa`, `hibernate` 등이 있다. 이들은 추상화 계층 인터페이스를 상속 받아서, 공통 행위에 대해서 각각의 형태에 맞게 정의되어 있다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/159f8e10-85a7-4655-aedc-670a6dde257c/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220225%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220225T075400Z&X-Amz-Expires=86400&X-Amz-Signature=2e6e78b91f07434d65ca523ab0c7a8d70f5e7e3022cd484fb445a74c1796a0cb&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

그렇기 때문에, Spring 은 추상화 계층의 인터페이스에서 선언해둔 3가지 행위에 대해서 호출을 했을 것이다.

물론, **객체 주입**의 경우는 <u>다른 클래스 계층이 역할을 수행</u>하고 있을 것이다.

그러면, **사용하는 입장에서는 뭐가 좋은가??** 

바로 인터페이스를 보고 단지 호출만 하면 된다는 것이다. `web mvc` 에서 우리는 `@Controller` 만 선언하면 되는 것이다.

이게 바로 spring 에서 `psa` 를 추구하는 이유이다.
