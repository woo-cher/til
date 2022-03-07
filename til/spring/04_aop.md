# Spring AOP

> **AOP (Aspect Oriented Programming)**


**AOP** 는 `관점 지향 프로그래밍` 이라고 부른다. 이에 대해 알기 전에, 프로그래밍 방법론에 대해 먼저 알아야 하겠다.

**프로그래밍 방법론**이란 쉽게 말해, 건축가가 건물을 지을 때 건축가의 선호도에 따라 건축 방법이 달라지는 것과 같다. 따라서, 프로그래밍을 할 때도 상황에 따라 적절한 방법과 기법을 활용해서 다른 방법으로 작업을 할 수 있는데 프로그래밍 방법론이란 이런 것을 말한다.

**방법론의 종류**에는 `절차 지향 프로그래밍`, `함수형 프로그래밍`, `객체 지향 프로그래밍` 등이 있다.

**JAVA** 의 경우 기본적으로 `객체 지향 프로그래밍` 그룹에 속한다.

# 📖 프로그래밍 방법론?

<aside>
💡

<b>1. 절차 지향 프로그래밍</b>

절차 지향 프로그래밍이란, 직역 그대로 `작업의 순서대로 처리` 하는 것이 아닌 프로시저를 호출하는 것을 통해 프로그래밍을 하는 방법이다.
우리가 동일한 입력값을 넣었을 때 항상 같은 결과값을 도출하는 것을 함수라고 부르는데, 프로시저는 특정 로직을 처리하기만 하고 결과 값은 반환하지 않는 서브 프로그램을 뜻한다.
<br>

<b>2. 함수형 프로그래밍</b>

데이터와 같은 자료를 수학적 계산의 개념으로 접근해서 특정 상태 값이나 가변 데이터를 지양하는 개발 방법이다. 즉, 쉽게 말해 동일한 입력 데이터에 대해 항상 동일한 결과 값을 도출하는 함수를 통해서 프로그래밍을 하는 방법이다.

<b>3. 객체 지향 프로그래밍</b>

프로그램을 단순 명령어로 보는 것이 아니라, “수 많은 객체들의 모임이다”는 시각으로 보고 프로그래밍을 하는 방법이다.

</aside>

Spring 은 JAVA 를 사용하기 때문에 객체 지향 프로그래밍(OOP) 에 대해 더 정리하고자 한다.

# ✅ OOP

`객체 지향 프로그래밍에 원칙`은 아래와 같다.

<br>

1. **단일 책임 원칙 (Single Responsibility Principle)**

- 객체는 오직 하나의 책임을 가져야 한다.
- 따라서, 변경이 발생했을 때 오직 하나의 이유만을 가져야 한다.

<br>

2. **개방-폐쇄 원칙 (Open-Close Principle)**

- 확장에 대해서는 개방적이고, 수정에 대해서는 폐쇄적이어야 한다.

<br>

```
💡 예시

스타 유닛을 만든다고 치자. 당신은 이런저런 공통사항을 생각하며 메서드와 필드를 정의한다. 
이 중엔 이동 메서드도 있다. 이동 메서드는 대상 위치를 인수로 받아 속도에 따라 대상 위치까지 유닛을 길찾기 인공지능을 사용해 이동한다. 
하지만 잠깐 곰곰이 생각해보니 이러면 브루들링 같은 유닛의 기묘한 움직임을 구현할 때 애로사항이 생길 것 같다.
당신은 고민하다가 이동 메서드에서 이동 패턴을 나타내는 코드를 별도의 메서드로 분리하고, 구현을 하위 클래스에 맡긴다.
그러면 브루들링 클래스에서는 이동 패턴 메서드만 재정의하면 유닛 클래스의 변경 없이 색다른 움직임을 보여줄 수 있다!
'유닛' 클래스의 '이동' 메서드는 수정할 필요조차 없다(수정에 대해선 폐쇄). 
그냥 브루들링 클래스의 이동 패턴 메서드만 재정의하면 그만인 것이다(확장에 대해선 개방).
```

<br>

3. **리스코프 치환 원칙 (Lioskov Substitution Principle)**

- 자식은 언제나 부모를 대체할 수 있다. 부모 클래스에 자식 클래스를 주입해도 잘 동작해야 한다.

```
💡 예시

마우스 클래스가 있다고 가정한다. 마우스는 왼쪽, 오른쪽, 휠 버튼이 항상 동일한 역할을 수행할 것이다.
만약, 일반적인 마우스가 아닌 측면 버튼이 추가된 마우스 ( 상속 ) 가 추가 되었다고 하자.
측면 버튼을 제외하더라도 왼, 오, 휠 버튼은 동일한 역할을 수행할 것이다.
```

<br>

4. **인터페이스 분리 원칙 (Interface Segregation Principle)**

- 클라이언트에 사용하지 않는 메소드는 사용해선 안 된다.

```
💡 예시

게임을 만드는데 충돌 처리와 이펙트 처리를 하는 서버를 각각 두고 이 처리 결과를 모두 클라이언트에게 보내야 한다고 가정하자.
그러면 Client라는 인터페이스를 정의하고 그 안에 충돌전달()과 이펙트전달(이펙트)를 넣어놓을 것이다.
이제 충돌 서버에서, Client 인터페이스를 구현할 것인데 충돌 서버에서는 필요가 없는 이펙트전달() 메소드가 포함되어 있다.
이렇게 되면, 필요 없는 함수임에도 구현해야 하는 문제가 생긴다.
그렇기 때문에, 인터페이스를 분리하여 충돌 전달 인터페이스와 이펙트 전달 인터페이스로 나누고, Client 인터페이스에서 이 두 인터페이스를 상속 받아서 사용한다.
```

<br>

5. **의존성 역전 원칙 (Dependency Inversion Principle)**

- 고수준 모듈이 저수준 모듈에 의존해서는 안된다.
- 저수준 모듈이 고수준 모듈에서 정의한 추상 타입에 의존해야 한다.

```
💡 예시

자동차라는 클래스가 있다. 현재 겨울이라고 가정하고, 이 클래스에는 아래와 같이 의존성을 주입했다.

(자동차) → (스노우 타이어)

근데 계절이 바뀌어 더 이상 스노우 타이어가 필요 없게 되었다. 그래서, 다른 타이어 타입으로 바꾸려고 하니 자동차가 스노우 타이어와 강하게 결합되어 있어서 수정 작업이 쉽지 않다.
또한, 자동차 클래스 내 코드 변경도 불가피하게 되었다.
따라서, 이 의존 관계를 느슨하게 해주기 위해 타이어 인터페이스를 정의하고, 타이어 타입으로 스노우 타이어 / 일반 타이어 / 광폭 타이어 등으로 분류 한다.
이렇게 되면, 변경이 발생하더라도 다른 객체에 미치는 영향을 최소화 할 수 있다.
```

# ✅ AOP 개념이 나온 이유

<br>
OOP의 개념으로 어플리케이션을 개발하면 책임과 관심사에 따라 클래스로 캡슐화한다. 바로 위의 5가지 원칙을 지향함으로서, 특정 한 부분에 변경이 발생했을 때 영향력이 크지 않게 된다.

그럼에도 OOP 에 대해 아쉬운 부분이 존재한다. 모든 클래스가 각자의 역할과 책임을 다하도록 설계되었지만 각각의 객체 군들이 공통적으로 수행하는 기능들이 흩어져서 존재한다는 것이다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b2696e5b-906d-4e08-a950-09fbd9303958/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220303%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220303T094507Z&X-Amz-Expires=86400&X-Amz-Signature=614ef6b73f3913bcbd4ea5fe53f955ea9bd3fd498872573cf68edce894cf3ab5&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

예를 들어, 특정 클래스 그룹에 대해서 “이 함수가 실행되기 전, 후로 로그를 남기고 싶은데..” 라는 상황이 발생할 수 있다. 나도, AOP 의 개념을 몰랐을 때 실제로 이러한 경험을 한 적이 있다.

그래서 나는 공통된 부분을 메소드로 분리해서 필요한 시점에 해당 함수를 호출하는 형태로 개발했었다. 분리한 함수를 호출만 하면 되지만, 실제로 해당 시점마다 이 부분을 넣는 것도 상당히 번거로웠다.

이러한 부분을 해소하기 위해 AOP 개념이 등장하게 된 것이다.

# ✅ AOP 사용 목적

`AOP` 는 앞서 설명한 것과 같이 관점 지향 프로그래밍이다. 어떠한 부분을 관점이라고 칭하는 것일까? 바로 위의 예시를 가져와 보겠다.

AOP 에서는 각 클래스 별 **수행하는 역할과 책임**을 `핵심 관점`으로 본다. 그리고, **로그를 남기거나 하는 등**의 부분을 `부가적인 관점` 이라고 보는 것이다.

이때, 특정 클래스 군들의 부가적인 관점들 중에서 공통적으로 사용되는 부분들을 **흩어진 관심사 (Cross cutting concerns)** 라고 부른다. 그리고 **이 부분들을** `Aspect`로 **모듈화** 해서 **핵심 관점으로 부터 분리해 재사용하는 것**이 AOP 의 목적이다.

이렇게 두 가지 관점으로 나누어서, 각각 모듈화해서 관리하는 것이 AOP 의 개념이다. 보통 부가 적인 관점의 경우 `logging`, `transaction` 등이 있을 것이다.

# ✅ AOP 의 주요 개념들

- **Aspect** : 여러 클래스에서 찾아 볼 수 있는 관심사들의 모듈화를 뜻한다.
- **JoinPoint** : 메소드 실행이나, 예외 처리 등 프로그램이 실행되는 동안의 시점을 말한다.
- **Advice** : 위 조인 시점에서 실행될 행위를 말한다. 이 유형으로는 “around”, “before”, “after” 등이 있다.
- **PointCut** : 조인 포인트보다 더 상세한 지점을 정의하고 싶을 때 사용한다. 보통 Spring 에서는 AspectJ pointcut 표현식을 사용한다.
  ( 예: A 메소드의 실행이 끝난 후 )
- **Target** : Aspect 를 적용하는 곳을 말한다.

# ✅ AOP 사용 방법

[spring-doc](https://docs.spring.io/spring-framework/docs/2.5.x/reference/aop.html) 를 보고 먼저 사용 방법에 대해서 알아보자.

**( 의존성 추가 )**

```java
// Maven
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aop</artifactId> 
</dependency>
```

## 1. @Aspect

<br>
첫번째로 `@Aspect` 주석이다. 클래스 레벨에 달 수 있으며, 해당 주석을 달아서 Spring 에게 흩어진 관심사에 대한 역할을 하는 클래스라고 알려줄 수 있다.

주의할 것은 `aop` 가 Spring container 가 관리하는 `bean` 객체들에 대해서 적용된다는 것이다. 따라서, aop 를 사용하기 위해 `@Aspect` 를 적용하는 클래스 또한 `bean` 으로 등록되어야 한다.

```java
package org.xyz;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
public class SomeAspectAdviser {
    // Cross cutting concerns
}
```

## 2. @Pointcut

```java
@Pointcut("execution(* transfer(..))")// the pointcut expression
private void anyOldTransfer() {}// the pointcut signature
```



두번째로 `@Pointcut` 어노테이션이다. 쉽게 말해서, **흩어진 관심사를 적용할 지점을 명세** 하는 곳이다. 이는 메소드 스코프에 적용 시킬 수 있다.

Advice 그룹들에 대해서, 공통적으로 적용되는 지점이 있을 때 Pointcut 으로 묶어서 Adviser 어노테이션에 사용할 수 있다.

표현식의 경우 내용이 많아서 doc 를 참고하는 것이 좋겠다. 

또한 표현식은 아래와 같이 `&&` 연산자와 `||` 연산자를 혼용해서 사용할 수 있다.

```java
@Pointcut("execution(public * *(..))")
private void anyPublicOperation() {}

@Pointcut("within(com.xyz.someapp.trading..*)")
private void inTrading() {}

@Pointcut("anyPublicOperation() && inTrading()")
private void tradingOperation() {}
```

## 3. @Advice 주석 group

흩어진 관심사들의 실행 시점을 정하고, 실행될 행위를 명세한다. 주석 안에 인자로는 위의 `pointcut` 표현식이 들어갈 수 있다.

### 3.1 @Before

- 메소드 호출 이전 시점에 실행한다

### 3.2 @After

- 메소드 호출 후의 시점에 실행한다

### 3.3 @Around

- 메소드 호출 시점을 가로채서, 실행 전/후의 로직을 추가 할 수 있다. ( 예 : 타임 워치 )

### 3.4 @AfterReturning

- 메소드 호출이 끝나고, 객체를 리턴하고 난 후의 시점에 실행한다.

### 3.5 @AfterThrowing

- 메소드 실행 중에 `Exception` 이 발생하여 throw 되기 전 시점에 실행한다.

# ✅ AOP 사용 해보기 (spring-boot)

나는 `SomeService` 의 비즈니스 로직 실행 전과 후로  어떠한 로그를 남기고 싶다고 가정했다. 그리고, `SomeService` 에서 작성한 모든 메소드들을 아래와 같이 정의했다고 가정한다.

```java
@Service
@Slf4j
public class SomeService {

		public void saveSomeObject() {
        log.info("saveSomeObject() called"); // 부가 관점
        log.info("saved : {}", new Bar()); // 핵심 관점
        log.info("saveSomeObject() end");  // 부가 관점
    }

    public void doSomething() {
        log.info("doSomething() called");
        log.info("do something!!");
        log.info("doSomething() end");
    }

    public String getSomeMessage() {
        log.info("getSomeMessage() called");
        log.info("will return : some message");
        log.info("getSomeMessage() end");
        return "some message";
    }
}
```

작성하고 보니 전, 후로 로그를 남기는 코드의 중복이 많이 발생한다. 이를 메소드로 따로 빼내면 되긴 하겠지만, 이를 호출하는 코드가 또 들어가야 하는 등의 작업이 생겨 번거롭다.

이제 위와 같은 상황에서 `spring aop` 를 사용해보자. 따라서, 나는 로그를 남기기 위한 aop 클래스를 아래와 같이 작성했다.

```java
@Aspect
@Component
@Slf4j
public class ServiceAdviserExample {

    // ~~Service 클래스의 save~~() 가 실행될 때
    @Pointcut("execution(* com.study.til.aop.*Service.save*(..))")
    public void loggingPointcut() {}

    @Before("loggingPointcut()") // 정의한 pointcut 사용
    public void beforeExample(JoinPoint joinPoint) {
        log.info("──────────────────── [Before] log, {}", joinPoint);
    }

    @After("loggingPointcut()") // 정의한 pointcut 사용
    public void afterExample(JoinPoint joinPoint) {
        log.info("──────────────────── [After] log, {}", joinPoint);
    }

    // @LoggingAspect 어노테이션이 적용된 곳
    @Around("@annotation(LoggingAspect)") // 
    public Object aroundAndAnnotationExample(ProceedingJoinPoint pjp) throws Throwable {
        log.info("──────────────────── [Around] before method proceed");
        Object retVal = pjp.proceed();
        log.info("──────────────────── [Around] after method proceed");
        return retVal;
    }

    @AfterReturning(value = "execution(* com.study.til.aop.SomeService.getSomeMessage())", returning = "returnObject")
    public void afterReturnExample(Object returnObject) {
        log.info("──────────────────── [AfterReturning] log, {}", returnObject);
    }
}
```

`@Aspect` 어노테이션을 적용해서 해당 클래스는 **흩어진 관심사에 대한 로직을 담당하는 친구**라고 spring 에게 알려주겠다. 또한, 앞서 말한 것과 같이 `aop` 는 bean 들에 대해서 적용이 되기 때문에 위 클래스를 `@Component` 로써 bean 으로 등록하겠다.

특정 주석이 적용된 함수에 대해 `aop` 를 적용해보기 위해 임시 주석 인터페이스를 아래와 같이 생성했다.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggingAspect {

}
```

그리고 기존 흩어진 관점을 분리했기 때문에 기존 `SomeService` 의 코드를 아래와 같이 수정했다.

```java
@Service
@Slf4j
public class SomeService {

    public void saveSomeObject() {
        log.info("saved : {}", new Bar());
    }

    @LoggingAspect // 임시로 만든 주석을 달았다
    public void doSomething() {
        log.info("do something!!");
    }

    public String getSomeMessage() {
        log.info("will return : some message");
        return "some message";
    }
}
```

작성한 `SomeService` 를 실행하기 위해, 간단한 테스트 코드를 작성하였다.

```java
@SpringBootTest
@Slf4j
public class AopTests {

    @Autowired
    private SomeService someService;

		@Test
    public void beforeAndAfter() {
        someService.saveSomeObject();
    }

    @Test
    public void afterReturning() {
        someService.getSomeMessage();
    }

    @Test
    public void aspectsWithAnnotation() {
        someService.doSomething();
    }
}
```

**(실행 결과)**

- **Before, After**

```java
com.study.til.aop.ServiceAdviserExample  : ──────────────────── [Before] log, execution(void com.study.til.aop.SomeService.saveSomeObject())
com.study.til.aop.SomeService            : saved : class com.study.til.spring_core.domain.Bar
com.study.til.aop.ServiceAdviserExample  : ──────────────────── [After] log, execution(void com.study.til.aop.SomeService.saveSomeObject())
```

- **AfterReturning**

```java
com.study.til.aop.SomeService            : will return : some message
com.study.til.aop.ServiceAdviserExample  : ──────────────────── [AfterReturning] log, some message
```

- **Around and @annotation expression**

```java
com.study.til.aop.ServiceAdviserExample  : ──────────────────── [Around] before method proceed
com.study.til.aop.SomeService            : do something!!
com.study.til.aop.ServiceAdviserExample  : ──────────────────── [Around] after method proceed
```

모든 부분에 대해서 정리를 하려니 내용이 너무 길어지는 것 같아 일부 생략했다.

다음에 실제로 사용할 기회가 생기면 doc 를 깊게 살펴보는 것이 좋겠다.



---

🔗 [https://devlog-wjdrbs96.tistory.com/165](https://tecoble.techcourse.co.kr/post/2021-06-25-aop-transaction/)

🔗 [https://namu.wiki/w/나무위키:대문](https://namu.wiki/w/%EB%82%98%EB%AC%B4%EC%9C%84%ED%82%A4:%EB%8C%80%EB%AC%B8)

🔗 [https://engkimbs.tistory.com/746](https://engkimbs.tistory.com/746)

🔗 [https://docs.spring.io/spring-framework/docs/2.5.x/reference/aop.html](https://docs.spring.io/spring-framework/docs/2.5.x/reference/aop.html)
