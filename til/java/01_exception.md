`운명` 이란 내가 노력하는 것에 따라 바뀔 수 있는 것을 말하고 `숙명` 이란, 내가 바꿀 수 없는 운명이다. `Exception` 은 `운명` 이고 `Error` 는`숙명` 이다.

<br>

> **ERROR (=숙명)**
>
> - 자바가 동작하는 환경에서 문제가 발생하는 것을 말한다
> 
> **예)** 운영체제, 메모리 등에서 발생한 어떤 문제


> **EXCEPTION(=운명)**
>
> - 개발자가 짠 코드에서 의도와는 다른 상황을 직면했을 때를 말한다
>
> **예)** 존재하지 않는 파일을 읽는 코드


# ✅ JAVA 에서 Exception 동작

<br>

예를 들어, 사용자가 입력한 값을 출력하는 App 이 있다고 하자. `1, 2, 3` 을 입력했더니 아무런 `예외` 가 발생하지 않고 정상 출력 되었다.

```
1
2
3
```

그 후에 사용자는 `1, 2 / 0, 3` 을 입력했더니 결과가 아래와 같았다.

```
1
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at com.study.til.exception.ExceptionApp.main(ExceptionApp.java:6)
```

결과를 보면, 어떤 예외가 발생되었는지 알려준다. 그런데, 예외가 발생한 지점에서 **프로그램의 동작이 멈추면서** 다음에 입력한 값인 `3`은 출력 되지 않는 모습이다.

실제로 프로그램이 동작 중에 예외가 발생했을 때, 서버가 어떠한 처리를 하지 않고 저 상태로 두는 것은 옳지 않다. 그래서 발생할 수 있는 `예외`에 대해 **어떤 처리 할 것 인지를 명시해줄 필요**가 있다.

<br>

# ✅ JAVA 예외 클래스

<br>

Exception 은 `Checked Exception` 과 `UnChecked Exception` 으로 구분하는데 그 전에 JAVA 에서 `예외클래스`가 어떻게 구분되어 있는지 살펴보자.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/79ac6fac-c6c7-474e-bc5c-9b49b27fb03f/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220412%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220412T094531Z&X-Amz-Expires=86400&X-Amz-Signature=bd3fca77c9a45f901ff45865e64a6f39e6f89363c83f040f4cd3db88c8a79f94&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

모든 예외는 `Throwable` 클래스를 상속 받는 구조로 되어있다. 여기서 `Error` 와 `Exception` 클래스로 구분된다.

앞서 언급한 바와 같이 `Error` 의 경우는 `OutOfMemoryError`, `StackOverflowError` 등 시스템 환경적인 측면에서 발생한 심각한 문제의 유형이다.

반면 `Exception` 은 개발자가 작성한 코드에서 발생하는 예외 상황으로 `RuntimeException`, `IOException` 등으로 구분되어 있다. 이 클래스들은 또 여러 클래스로 나누어지는데, 보다 세세한 예외 상황으로 구분 된다고 생각하면 된다.

여기서 `*RuntimeException` 은 발생한 `예외`가 `checked` 인지 `unchecked` 인지 구분하는 기준이다.

- `Unchecked Exception`  은 `RuntimeException` 을 **포함한 자식 클래스들**이다
- `Checked Exception` 은 `Exception` 에서 `RuntimeException` 을 **제외한 나머지 클래스**들이다

# ✅ Checked & UnChecked

<br>

### 1️⃣ Checked Exception

<br>

`Checked Exception` 은 개발자가 반드시 예외 처리에 대한 조치를 해야만 한다. 만일, 조치를 하지 않으면 **컴파일러 단계**에서 더 이상 진행하지 못하도록 막는다

아래 코드는 `FileWriter` 를 사용한 코드이다. 이는 `IOException` 이 발생할 수 있다.

```java
public class CheckedException {
    public static void main(String[] args) {
        FileWriter f = new FileWriter("myFile.txt");
        f.write("write file!");
        f.close();
    }
}
```

그런데, **컴파일러**에서 빨간 줄을 그으면서 오류를 말해준다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/63fbf5f8-b2cd-4bcb-85db-f109ca8afbcf/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220412%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220412T094549Z&X-Amz-Expires=86400&X-Amz-Signature=705d1cc60cf97ae592c7768a6c610b4af9dae0ea02632173d2138ad7b0091298&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

메시지를 보면 `IOException` 예외를 제어하지 않았다는 말이다. 즉, 이는 **개발자가 반드시 해당 예외에 대한 조치를 해주어야 한다**는 말이다. **예외 조치 방법**은 다음 단계에 있다.

<br>

### 2️⃣ UnChecked Exception

<br>

`Unchecked Exception` 은 위 유형과 달리, 컴파일러 단계에서 확인되지 않는다. 즉, 프로그램은 정상으로 실행이 된다는 말이다. 따라서, 프로그램이 실행 중에 어떤 예외를 직면할 때 예외를 발생 시킨다.

가장 쉬운 예는 어떤 숫자를 `0` 으로 나누기 하는 상황이 발생할 때이다.

```java
public class UnCheckedException {
    public static void main(String[] args) {
        System.out.println(2 / 0);
    }
}

// 실행 결과
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at com.study.til.exception.UnCheckedException.main(UnCheckedException.java:5)

Process finished with exit code 1
```

위 코드는 컴파일러 단계에서 아무런 문제가 없다고 나온다. 하지만, 코드를 실행해보면 `RuntimeException` 의 자식 클래스 중 하나인 `ArithemticException` 예외가 발생한 것을 볼 수 있다.

즉, `UncheckedException` 은 프로그램이 **실행하는 중**에 **(런타임)** 발생할 수 있는 예외이며, 이에 대한 처리를 하지 않아도 **컴파일러가 막지 않는다는 것**이다.

<br>

### 💡 Exception 에 따른 Spring Transaction 처리

<br>

기본적으로 `Spring` 에서 제공하는 `Transaction` 은 발생한 예외의 종류에 따라 아래와 같이 처리한다고 나와있다.

> While the Spring default behavior for declarative transaction management follows EJB convention **(roll back is automatic only on unchecked exceptions)**, it is often useful to customize this behavior.
>

번역하면 `Spring` 은 `EJB` 가 `transaction` 을 기본으로 관리하는 **관습**을 따른다고 되어있다. 이 관습이란 것은 유일하게 `unchecked exception` 의 경우만 롤백 처리를 자동으로 해준다는 것이다.

하지만 마지막 구문에서도 언급하듯이, 이는 `spring` 에서 기본적으로 설정된 값이며 `개발자` 가 이 부분에 대해 customize 할 수 있다고 나와있다.

즉, 상황에 따라 `checked` 든 `unchecked` 든 개발자가 원하면 rollback 여부를 결정할 수 있다.

<br>

# ✅ 예외 처리 방법

<br>

예외를 처리하는 방법은 아래와 같이 3가지가 있다.

<br>

### 1️⃣ 예외 복구 (try-catch)

`try-catch` 으로 예외를 처리하는 방법이다. 이는 프로그램 실행 중 관련 **예외가 발생해도 프로그램이 중단되지 않는다**. 즉, `catch` 블럭에서 예외 관련 로직을 처리 한 뒤  정상적인 흐름으로 진행된다.

```java
try {
		// 예외가 발생할 가능성이 있는 로직
} catch (SomeException e) {
		// 예외 발생 시 처리할 로직
} finally {
		// 리소스 반환 등의 작업
}
```

<br>

- 💡 **try-with-resources ( JAVA 7** ⤴️ )


먼저 `Checked Exception` 이 발생할 수 있는 `FileWriter` 에 대해서, 일반적인 `try-catch` 문을 작성했더니 리소스를 반환하는 부분에서 또 컴파일러 오류가 발생했다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/813caaa0-4a34-43f7-900e-98cfcf46e187/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220412%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220412T094658Z&X-Amz-Expires=86400&X-Amz-Signature=5aca53d130b43a67dff81c0cc2e770ec659c0071f556498f9ebba967ec32c495&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`f.close()` 는 로직을 수행한 뒤에 리소스를 반환하는 코드이다. 이 코드는 `try` 문에서 절대로 사용할 수 없다.

```java
try {
    f = new FileWriter("myFile.txt"); 
    f.write("write file!");
    f.close(); // close() 를 처리하기 전에 예외가 발생할 수 있음
} catch (IOException e) {
    // do something
}
```

  만약 위와 같은 형태로 코드를 작성했다고 해보자. 이렇게 되면, 이전 **로직에서 예외가 발생했을 때 리소스를 정상적으로 반환하지 못하는 경우가 발생한다.**

  따라서, `f.close()` 를 위한 try-catch 문을 또 작성했더니 너무 복잡해졌다.

```java
FileWriter f = null;

try {
    f = new FileWriter("myFile.txt");
    f.write("write file!");
} catch (IOException e) {
    // do something
} finally {
    try {
        if (f != null) {
            f.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

  이때 사용할 수 있는 문법이 바로 `try-with-resource` 이다. 이는 반드시, `AutoClosable` 인터페이스를 상속 받는 클래스에만 사용할 수 있다.

```java
/**
 *  Using AutoClosable
 *
 *  @see java.lang.AutoCloseable
 */
public static void writeAutoClosable() {
    try (FileWriter f = new FileWriter("myFile.txt")) {
        f.write("write file!");
    } catch (IOException e) {
        // do something
    }
}
```

  코드가 훨씬 간결해졌다. 그리고, 이 문법은 **리소스 반환 작업을 자동으로 해준다**!

<br>

### 2️⃣ 예외 처리 회피 (throw ~)

<br>

`예외 처리 회피`는 throw 를 통해 다른 클래스로 예외를 던지는 것이다. 예외를 받은 클래스에서 발생한 예외를 처리하도록 책임을 넘기는 방법이다.

```java
public static void exceptionThrow() throws IOException {
    FileWriter f = new FileWriter("myFile.txt");
    // do something
}
```

<br>

### 3️⃣ 예외 전환 (throw new ~)

<br>

`예외 전환`은 범주가 큰 예외를 잡았을 때, 이미 정의해둔 다른 예외 클래스 등으로 `throw` 하는 방법이다. `catch` 한 예외를 더 상세한 예외 클래스로 던질 수 있다.

```java
try {
	// some user select ...
} catch (SQLException e) {
	...
	throw new UserNotFoundException("사용자를 찾을 수 없습니다");
}
```

<br>

---

🔗 [https://www.youtube.com/watch?v=HZL4iUUx4_E&list=PLuHgQVnccGMCrFJLxpjhE0N5tvOVxJuVB&index=1&ab_channel=생활코딩](https://www.youtube.com/watch?v=HZL4iUUx4_E&list=PLuHgQVnccGMCrFJLxpjhE0N5tvOVxJuVB&index=1&ab_channel=%EC%83%9D%ED%99%9C%EC%BD%94%EB%94%A9)

🔗 [https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html)

🔗 [https://www.nextree.co.kr/p3239/](https://www.nextree.co.kr/p3239/)

🔗 [https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/transaction.html](https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/transaction.html)
