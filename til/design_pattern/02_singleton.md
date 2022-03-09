# Singleton Pattern


> 📖 싱글톤 (Singleton) 패턴
>
> - 클래스 인스턴스를 오로지 한 개만 제공하기 위한 패턴


싱글톤은 시스템 런타임, 환경 세팅 정보, 인스턴스가 여러개 일 때 문제가 생길 수 있는 경우에 인스턴스를 단 한개만 만들어서 제공하는 클래스이다.

예를 들어 네이버를 들어가면 `다크 모드로 보기` 라는 버튼이 있다. 이 버튼을 누르면, 네이버 안에서 어떤 작업을 하던 간에 다크 모드로 쭉 유지가 된다.

만약 내부적인 어떤 코드에서, `Mode` 객체가 있다고 한다면 해당 객체는 다른 어떤 클래스에서든 간에 모드가 변경되어서는 안 될 것이다.

또 다른 예로, 게임을 할 때 `setting` 환경이 있다. 이 환경을 저장하는 객체가 여러 개라고 가정해보자. 내부적인 코드에 오류로 인해서, 특정 맵에 들어갔을 때 다른 환경 셋팅 객체인 B 를 읽어들였다고 해보자. 사용자는 이전과 키 환경 세팅이 달라서 화를 내고 있을 것이다.

# ✅ 싱글톤 패턴의 기본

우선 싱글톤의 특징은 생성자를 `private` 로 두는 것이다.
다른 곳에서 **생성자를 통해 새로운 객체를 생성하지 못하도록** 막는 것이다.

그래서, 해당 자원에 **접근**하기 위해서는 오로지 `getInstance()` 를 호출해야 한다.

```java
public class GameSettings {

    private static GameSettingsinstance instance;

    private GameSettings() {}

    public static GameSettings getInstance() {
        if (instance== null) {
            return new GameSettings();
        }

        return instance;
    }
}
```

그런데, 위 코드는 `멀티 쓰레드 환경`에서 **문제가 있다**. 
여러개의 쓰레드가 동작하는 중에 `getIntance()` 에 동시에 접근했다고 할 때, if 문을 들어온 쓰레드 모두가 검사하게 될 것이고 
결과적으로 객체 생성 로직이 2번 실행 될 수 있다. 이런 상황을 `Thread safe` 하지 않다고 한다.

# ✅ Thread Safe 하는 방법들과 특징


## 1️⃣ sychronized 키워드 사용하기

<br>

첫번째로 메소드 단 앞에 `sychronized` 키워드를 붙이는 것이다. 이 키워드는 해당 자원에 접근 권한이 있는 쓰레드만이 접근하되, 타 쓰레드는 접근조차 하지 못하도록 막는 것이다.

```java
public static synchronized GameSettings getInstance() {
      if (instance == null) {
          return new GameSettings();
      }

      return instance;
}
```

하지만 이 메커니즘을 사용함으로서 오버헤드가 발생하며, 경우에 따라 성능 저하가 발생할 수도 있다.

## 2️⃣ 이른 초기화 (eager initialization)

<br>

만약, 객체를 생성하는데 자원적 부담이 전혀 없다면 아래와 같이 객체를 미리 생성해두는 `이른 초기화(eager initialization)` 방법을 사용 해도 된다.

```java
public class GameSettings {
    private static final GameSettings INSTANCE = new GameSettings();

    private GameSettings() {}

    public static synchronized GameSettings getInstance() {
        return INSTANCE;
    }
}
```

하지만 만약 **반대의 상황** ( 객체 생성의 비용이 큰 ) 이라고 해보자. 미리 객체를 생성해두었는데, 바로 사용하지는 않는다면 이는 결국 `자원 낭비`가 된다.

그렇다면, `getInstance()` 가 호출 되는 시점에 `Thread safe` 하게 객체를 생성하는 방법은 무엇일까?

## 3️⃣ Double checked locking

<br>

이 방법은 `block` 영역에서 `synchronized` 키워드로 동기화를 거는 방법이다. 우선적으로 객체의 null 여부를 판단하고, 동기화 블럭 내에서 한번 더 검사한다는 점에서 이름이 붙었다.

```java
public class GameSettings {

    private static volatile GameSettings instance;

    private GameSettings() {}

    public static GameSettings getInstance() {
        if (instance == null) {
            synchronized (GameSettings.class) {
                if (instance == null) {
                    return new GameSettings();
                }
            }
        }

        return instance;
    }
}
```

또 다른 점은 필드 변수에 `vloatile` 키워드가 붙는 것이다. 이는 JAVA 1.5 버전부터 동작하는 키워드인데, 개념만 보자면 아래와 같다. 깊게 가자면 또 내용이 길고 복잡해져서 우선 생략했다.


```java
💡 vloatile

해당 키워드가 붙은 변수는 무조건 메인 메모리로부터 읽기, 쓰기의 작업이 수행될 것을 `보장`
하는 것이다. 멀티 쓰레드 환경의 성능적 차원에서 CPU 캐시를 사용하지 않고, 메인 메모리가 직접
관장하는 것을 보장하는 것이다.
```

또한 나는 여기서 한 가지 의문이 생겼다. 1번의 `동기화 키워드 사용 방법`과 `위의 동기화`가 **무슨 차이가 있을까?**

<br>

```java
💡 synchronized method VS synchronized block

두 방법의 가장 큰 차이점은 해당 자원을 잠그는(lock) 범위에 있다. 이름 그대로, 전자의 경우
메소드 전체에 대해 lock 을 거는 것이며 후자는 동기화 영역에 대한 lock 을 거는 것이다.

만일, 두 쓰레드 A, B 가 동일한 메소드에 동시에 접근했다고 해보자.
이 경우 전자의 방법이라면 A 나 B 중 한명이 메소드에 접근했을 것이고, 접근 못한 쓰레드는
메소드 전체의 로직이 끝날 때까지 접근을 할 수 없다.

후자의 방법이라면, 두 메소드에 동시에 접근을 하되 동기화 블럭 내에서는 한 쓰레드가 사용이
끝날 때까지 남은 쓰레드는 기다려야한다.

성능적 측면에서 본다면, 당연히 후자인 블럭 동기화가 좋다고 볼 수 있다. 왜냐하면, 멀티 쓰레드
작업이 빈번히 일어나는 환경에서, 동시에 인스턴스 생성 블럭에 접근했을 경우에만 동기화 작업을
처리하기 때문이다. 또한, 동기화가 걸린 영역을 누군가 사용하는 동안에 다른 쓰레드는 영역 밖의 
자원은 접근하여 처리 할 수 있기 때문이다.
```

## 4️⃣ static inner class 🌟🌟🌟

<br>

`holder` 클래스를 만들어서 해당 클래스를 통해서 인스턴스를 얻을 수 있도록 하는 방법이다.

```java
public class GameSettings {

    private GameSettings() {}

    private static class SettingsHolder {
        private static final GameSettings INSTANCE = new GameSettings();
    }

    public static GameSettings getInstance() {
        return SettingsHolder.INSTANCE;
    }
}
```

이 코드에서 `getInstance()` 가 호출될 때, inner 클래스가 생성되고 JVM 은 `ClassLoading` 에 잠금(lock)을 사용하기 때문에 이는 멀티 쓰레드 환경에서도 안전하다. 이후에 호출이 되면, 이미 로드된 내부 클래스를 다시 불러와 기존의 인스턴스를 반환한다.

이 방법의 경우, 동기화 블럭을 사용하지도 않으며 코드가 간결하면서도 멀티 쓰레드 환경에서 안정성이 보장된다. 또한, 필요에 의해 객체를 생성하기 때문에 lazy-initialization 도 만족하는 방법이다.

---

🔗 [https://stackoverflow.com/questions/20906548/why-is-synchronized-block-better-than-synchronized-method](https://stackoverflow.com/questions/20906548/why-is-synchronized-block-better-than-synchronized-method)

🔗 [https://stackoverflow.com/questions/8521819/performance-of-synchronize-section-in-java](https://stackoverflow.com/questions/8521819/performance-of-synchronize-section-in-java)

🔗 [https://parkcheolu.tistory.com/16](https://parkcheolu.tistory.com/16)

🔗 [https://www.youtube.com/watch?v=OwOEGhAo3pI](https://www.youtube.com/watch?v=OwOEGhAo3pI)

🔗 [https://www.youtube.com/watch?v=bHRETd1rFfc&list=RDCMUCwjaZf1WggZdbczi36bWlBA&start_radio=1&rv=bHRETd1rFfc&t=8](https://www.youtube.com/watch?v=bHRETd1rFfc&list=RDCMUCwjaZf1WggZdbczi36bWlBA&start_radio=1&rv=bHRETd1rFfc&t=8)
