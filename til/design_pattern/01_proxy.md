> 직역하면 “대리” 라는 의미를 가지는 프록시 패턴은 특정 객체에 대한 접근을 제어하거나 기능을 추가할 수 있는 디자인 패턴이다
>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/93cfb7cb-59ea-4b45-a97b-d5e14fe1176b/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220307%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220307T100213Z&X-Amz-Expires=86400&X-Amz-Signature=5bca5109e9bc8dca26692e756577eb1244ca535fc100a91ad0c115117ef6cb2c&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

프록시 패턴의 목적은 기존 서비스 로직에서 특정한 액션을 취하고 싶을 때, 서비스 코드를 직접 수정하지 않고 프록시 클래스 형태로 접근하게 해서 원하는 액션을 추가하는 것에 있다. 따라서 마치 기존 서비스 클래스의 `대리인`의 역할을 하는 듯 하다.

예제를 적용해보기에 앞서 장점과 단점을 먼저 살펴보겠다.

# 📔 Proxy pattern 의 장점과 단점

## 👍 장점
<br>

- **기존 코드의 변경 없이** 새로운 기능을 추가 할 수 있다. ( logging, caching, time watch ... )
- 기존 코드는 **본인이 해야 하는 역할만 수행**할 수 있다. **( OOP 단일 책임 원칙 )**
- 리턴 할 객체를 수정하거나, `초기화 지연 (lazy-init)`을 적용하는 등 다양하게 활용 할 수 있다.

```java
💡 lazy-init

특정 모듈이 제공하는 서비스 크기가 방대해서 메모리를 많이 잡아먹는다고 할 때, 새로운 
객체를 생성 하는데 부담이 되는 상황에서 사용되곤 한다. lazy-init 은 객체를 미리 생성해두지
않고, 메소드가 실행되고 난 이후에 객체를 주입하는 방법이다.
```

## 👎 단점
<br>

- 기존에 비해 **코드가 복잡해진다.** ( 프록시 클래스, 인터페이스, 의존성 주입 등의 추가 )
- 만약, 기존 **서비스 클래스가 인터페이스를 구현하는 모양이 아니라면**, 작업이 많아진다.

(<b id="target">상속</b>으로 프록시 형태를 취할 수는 있지만 `제약사항`이 많이 따른다 )

```java
💡 상속

상속은 부모 클래스의 모든 것(장점, 단점)을 그대로 물려받는다. 이에 따라, 자식에서는 불필요한
부분일지라도 상속을 받아야 한다. 만약, 연속된 상속 계층 형태로 구현되어 있다고 하자.
자식 어느 부분에서 오류가 발생한다면, 정확하게 어느 계층에서 문제가 발생했는지 찾기가 어렵다
```

# ✅ Proxy Pattern 을 사용해보자
<br>

먼저 **핵심 서비스 로직**이 아래와 같다고 해보자.

```java
public class GameService {

    public void startGame() {
        System.out.println("Welcome game!");
    }
}
```

그리고 클라이언트 단에서 해당 메소드를 사용하며, 이 **메소드가 실행되는 시간을 재는 코드**를 추가하고 싶다고 해보자. **(Time watch)**

```java
public class Client {

    public static void main(String[] args) {
        GameService gameService = new GameService();
        gameService.startGame();
    }
}
```

만약 시간을 재려고 한다면, `GameService` 의 메소드 내에서, 시간을 재는 코드가 들어갈 것이다.

```java
public class GameService {
    public void startGame() {
        // start time watch
        System.out.println("Welcome game!");
        // end time watch
    }
}
```

이러한 형태의 조치는 좋지 않은 방법이다. 우선 `GameService`는 **하나의 역할에 대한 책임**을 져야만 하는데
기존 로직에 시간을 재는 코드가 추가되면서 책임이 늘었다. 
또한, 메인 역할과 상관 없는 코드가 추가됨으로 인해 **가독성이 떨어진다**.

이제, `프록시 패턴`을 적용해서 책임을 분리 시켜 보자. 먼저, `GameService` 의 핵심 로직을 인터페이스를 구현하는 형태로 만든다.

```java
public interface GameService {
    void startGame();
}

public class DefaultGameService implements GameService {

    @Override
    public void startGame() {
        long startTime = System.currentTimeMillis();
          System.out.println("Welcome game!");
    }
}
```

그리고, 해당 `DefaultGameService` 의 대리 역할을 수행할 프록시 클래스를 만들어준다. 프록시 클래스는, 상위 계층으로 만들어둔 인터페이스를 필드 변수로 추가해준다.

그리고 해당 필드 변수에 의존성을 주입하기 위한 **생성자**를 추가한다. 이제, 정의만 해두었던 인터페이스 메소드를 호출하고 해당 시점 전, 후로 시간을 재는 코드를 추가한다.

```java
public class GameServiceProxy implements GameService {

    private GameService gameService;

    public GameServiceProxy(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void startGame() {
        long startTime = System.currentTimeMillis();
        this.gameService.startGame();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
```

위 코드만 보았을 때, 프록시의 `gameService.startGame()` 에서는 아직 어떤 서비스 클래스의 함수인지 모른다.
왜냐하면, **의존성을 주입해주지 않았기 때문**이다.

이제 클라이언트에서 똑같이 서비스 메소드를 사용한다고 해보자.

```java
public class Client {

    public static void main(String[] args) {

        // 이제 Client 는 Proxy 를 거쳐서 startGame() 메소드를 호출하게 된다.
        GameService gameService = new GameServiceProxy(new DefaultGameService());
        gameService.startGame();
    }
}
```

```java
(실행결과)
Welcome game!
0                 <------ print 만 찍어서 매우 짧은 시간이 걸렸기 때문이다.
```

만약에, `gameService` 의 실행 로직이 메모리를 엄청 소모해서 새로운 객체 생성에 어려움이 있을 것 같다면 아래와 같이 `lazy-init` 을 적용 해볼 수도 있을 것이다.


```java
public class GameServiceProxy implements GameService {

    private GameService gameService;

    @Override
    public void startGame() {
        long startTime = System.currentTimeMillis();

        // 초기화 지연 (lazy-init)
        if (this.gameService == null) {
            this.gameService = new DefaultGameService(); // 메소드 호출 시 의존성 주입
        }

        this.gameService.startGame();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
```

프록시 패턴을 사용해서 기존의 서비스 로직 코드를 건들지 않고 `timewatch` 코드를 추가했다. 기존 서비스 클래스는 자기가 하던 일을 그대로 수행하기만 하면 된다. 이에 따라, 클래스 파일이나 인터페이스가 추가되면서 다소 복잡해 보일 수도 있는 것 같다.

하지만, 이렇게 함으로써 코드의 재사용이 늘어나고 클래스의 책임을 분리함으로서 더 깔끔한 느낌이 들었다. 확실한 장점은 실전에서 적용해봐야 알 수 있을 것 같다.

마지막으로, [상속 형태로 프록시 패턴을 구현](#target)했을 때 어떤지 정확히 이해가 가지 않아서 구현해보았다.

## ❓ 상속 형태로 취한 Proxy Pattern
<br>

만약, `GameService` 의 핵심 서비스 로직이 인터페이스를 구현한 모양이 아니라 일반적인 클래스 형태라고 해보자. 모양은 맨 처음과 같다.

```java
public class GameService {

    public void startGame() {
        System.out.println("Welcome game!");
    }
}
```

그렇다면, 인터페이스를 사용하지 않고 상속으로 프록시를 구현한다면?? 위 서비스 클래스를 상속 받아 `@override` 하는 모양일 것이다.

```java
public class GameServiceChildProxy extends GameService {

    @Override
    public void startGame() {
        long startTime = System.currentTimeMillis();
        super.startGame();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
```

부모의 함수와 동일한 함수를 가져와서 재정의 할 때, `super` 를 사용해서 부모의 서비스 로직을 그대로 가져오고, 해당 호출 시점의 전, 후로 타임 워치 코드를 추가할 수 있을 것이다.

그런데, 만약 `GameService` 클래스가 `final` 이라면??

**당연히 상속을 받을 수 없다.** 그리고, `상속 관계`이기 때문에 프록시는 **부모의 모든 것**을 물려 받는다. 어쩌면, **프록시에게는 전혀 필요 없는 자원일지라도 말이다.**

여러 관계로 상속 받은 관계라면, 어느 시점에서 문제가 발생했는지 정확히 예측하기도 어렵다. 이 때문에, 상속 형태로 구현하는 것은 좋은 방법이 아니라고 하는 것 같다. 좋은 점 보다는 제약 사항이 더 많이 보이기 때문이다.

## ❓ Proxy class 수요가 많을 때
<br>

지금까지 서비스 클래스를 인터페이스를 구현해서 상속 받는 형태를 만들고
여기에 `Proxy Pattern` 을 사용해보았다. 직접 개체마다 일일히 만드는 형태로 취했기 때문에 다소 `static` 하다고 볼 수 있다.

만약 **프록시를 만들고 싶은 서비스 클래스가 많은 상황이** 온다면?? 클래스마다 프록시를 모~두 만들어 주어야 한다. 여기에서는 많은 코드 중복이 발생할 수 있고, 파일이 너무 많아져서 관리가 힘들 것이다.

너무 번거롭다. 이럴 때를 위해서 JAVA 에서는 `Dynamic` 하게 Proxy 를 구현할 수 있는 방법을 제공하고 있다.

# 📚 Java 의 Dynamic Proxy
<br>

> JAVA 에서 어플리케이션 실행 중에 프록시 인스턴스를 동적으로 생성하는 것을 지원하는 것으로 자바의 reflection 기능으로 만들어져 제공한다


# 👍 특징

- **프록시를 만들어야 하는 클래스가 많을 경우** 활용하면 좋다.
- 컴파일 시점에 프록시 객체 의존성이 정해지는 것이 아니기 때문에, 하나의 `dynamic proxy` 메소드를 만들어 두면, 다양한 서비스 클래스에 프록시를 적용시킬 수 있다.

# ✅ Dynamic Proxy 를 사용해보자
<br>

위의 프록시 패턴에서 가정한 예제처럼, `GameService` 실행 전 후로, 로그를 찍고 싶다고 가정해보자.
그리고, 위에서 프록시 패턴을 적용할 때 만들어둔 인터페이스가 있는데 이의 자료형을 `I` 라고 가정한다. 그럼, JAVA의 `Dynamic Proxy` 를 사용하는 방법을 살펴보자

```java
public I getSomeDynamicProxy(I i) {
    return (I) Proxy.newProxyInstance(this.getClass().getClassLoader(),
            new Class[]{I.class}, (proxy, method, args) -> {
                // 메소드 실행 전 처리할 것
                method.invoke(target, args);
                // 메소드 실행 후 처리할 것
                return null;
            });
}
```

JAVA 의 `Proxy` 클래스에서 `newProxyInstance` 메소드를 활용하여 다이나믹 프록시를 만들 수 있다.

해당 메소드를 타고 들어가면 아래와 같이 생겼다.

```java
@CallerSensitive
public static Object newProxyInstance(ClassLoader loader,
                                      Class<?>[] interfaces,
                                      InvocationHandler h) {
    Objects.requireNonNull(h);

    final Class<?> caller = System.getSecurityManager() == null
                                ? null
                                : Reflection.getCallerClass();

    /*
     * Look up or generate the designated proxy class and its constructor.
     */
    Constructor<?> cons = getProxyConstructor(caller, loader, interfaces);

    return newProxyInstance(caller, cons, h);
}
```

첫째로 위 코드를 보면, `Reflection` 을 활용하는 것을 알 수 있다. 이는 특정 객체를 가지고 해당 클래스의 정보를 분석해서 필요에 따라, 클래스 필드를 가져오거나 클래스에 정의된 메소드를 제어할 수 있다.

또한, 위 메소드 인자 값을 보면 `InvocationHandler` 가 들어가는데 이는 인터페이스이며, `invoke()` 라는 메소드가 선언 되어 있다.

우리가 다이나믹 프록시를 사용하기 위해서는 위 인터페이스에 선언된 메소드인 `invoke` 를 어떻게 사용할지 정의해주면 되는 것이다.

다시 돌아와서, 나는 메소드 실행 전, 후로 로그를 남기고 싶다. 이를 코드에 적용 시켜보자.

```java
public class DynamicProxy {

    public GameService getLogServiceProxy(GameService target) {
        return (GameService) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{GameService.class}, (proxy, method, args) -> {
                    System.out.println("메소드 실행 전 로그");
                    method.invoke(target, args);
                    System.out.println("메소드 실행 후 로그");
                    return null;
                });
    }
}
```

위에서 사용된 `GameService` 객체는 프록시 패턴을 사용하기 위해 만들었던 인터페이스이다. 우리는 로그 기록을 남길 프록시가 필요하므로 위와 같이 메소드를 만들었다. 이제 클라이언트에서 사용해보자.

```java
public class Client {
    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy();
        GameService gameService = dynamicProxy.getLogServiceProxy(new DefaultGameService());
        gameService.startGame();
    }
}
```

`DynamicProxy` 를 통해서, 로그를 남기는 프록시를 가져와준다. 물론, 메인 역할을 수행할 서비스는 만들어둔 `DefaultGameService` 가 되겠다.

```java
public class DefaultGameService implements GameService {

    @Override
    public void startGame() {
          System.out.println("Welcome game!");
    }
}
```

이렇게 되면, 리플렉션을 통해서 `DefaultGameService` 클래스를 분석하고, 메소드인 `startGame()` 에 접근이 가능한지 여부를 판단한 뒤에 해당 메소드를 `invoke` 할 것이다.

하지만, `invocationHandler` 에서 나는 메소드를 실행하기 전, 후로 로그를 남기는 로직을 추가했다. 결과를 보면 아래와 같다.

```java
메소드 실행 전 로그
Welcome game!
메소드 실행 후 로그
```

추가로 예제를 하나 더 해보자. 만약, 메소드 실행에 걸린 시간을 계산하는 프록시가 필요하다면 아래와 같이 정의할 수 있겠다.

```java
public GameService getTimerServiceProxy(GameService target) {
    return (GameService) Proxy.newProxyInstance(this.getClass().getClassLoader(),
            new Class[]{GameService.class}, (proxy, method, args) -> {
                long before = System.currentTimeMillis();
                method.invoke(target, args);
                System.out.println("걸린 시간 : " + (before - System.currentTimeMillis()));
                return null;
            });
}
```

그럼, 클라이언트에서는 `dynamicProxy.getTimerServiceProxy(new DefaultGameService());` 이렇게 프록시를 얻을 수 있을 것이다.

```java
Welcome game!
걸린 시간 : 0  < --- 단순 print() 하나만 찍기 때문에 시간이 얼마 걸리지 않음
```

## 💡 Spring AOP

지금까지 `프록시 패턴`을 적용해보았고, 프록시를 만들어야 하는 클래스가 많아졌을 때 사용할 수 있는 `DynamicProxy` 까지 살펴보았다.

하지만 Spring 에서 프록시가 필요할 때 위 방법을 사용하지는 않는다. 왜냐하면, Spring 에서는 개발자로 하여금 프록시를 아주 간편하게 만들 수 있는 환경을 제공해주기 때문이다.
이 모듈이 바로 `Spring aop` 인데, 이에 대한 개념은 [이곳](../spring/04_aop.md) 정리해두었다.

---

🔗 [https://youtu.be/tes_ekyB6U8](https://youtu.be/tes_ekyB6U8)

🔗 [https://youtu.be/267d9IfwRdc](https://youtu.be/267d9IfwRdc)

🔗 [https://youtu.be/z52yw8fnUSA](https://youtu.be/z52yw8fnUSA)

🔗 [https://youtu.be/8zExKZxSaHE](https://youtu.be/8zExKZxSaHE)
