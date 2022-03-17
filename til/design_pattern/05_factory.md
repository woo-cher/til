##:five: **팩토리 메소드 패턴 (Factory Method Pattern)**

>`필요한 객체를 찍어서 제공하는 공장` 이다. 즉, 특정 타입에 해당하는 객체 의존성 주입의 책임을 팩토리 클래스에게 위임하도록 설계한 원칙이다.

<br>

# ✅ 설계 구조

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7d004383-d726-42cf-be58-685ba42f3086/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220317%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220317T070031Z&X-Amz-Expires=86400&X-Amz-Signature=e4d9126ec55b6acf6b47a7af780b0fb986094681f06068ff7a816e14757f9bff&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`팩토리 메소드 패턴` 의 설계 구조는 위와 같다. 먼저 `Creator` 는 객체 생성 행위를 추상화한 인터페이스이다. 이를 상속 받아서, 실질적인 객체 생성 행위를 세부적으로 정의한 클래스가 `ConcreteCreator` 이다. 해당 클래스에서는, 조건을 따져서 알맞은 `Product` 형 객체를 리턴해준다.

이 패턴을 적용하면, `Product` 클래스에서는 객체의 의존성을 주입하는 코드를 궁금해 할 필요가 없어진다. 기존에 `new` 형태로 모두 작성된 코드들이 팩토리 클래스에 캡슐화 되었기 때문에, 단지 객체를 가져오는 메소드만 호출해서 객체를 얻으면 되는 것이다.

사실, 필요한 `팩토리`가 하나 뿐이라면 추상화 계층인 `Creator` 까지 만들 필요는 없다. 팩토리 패턴에서 한 단계 더 추상화한 `추상 팩토리 패턴` 이 또 있는데 이를 구현할 때는 해당 계층을 구현해주어야 한다. 추상 팩토리 패턴은 다른 페이지에서 다루어야겠다.

<br>

# ✅ 예시로 이해하기

<br>

`약국` 이 있다. 약국에는 목감기약, 두통약, 소화제 등의 다양한 약들이 있다. 이때, 손님이 가게로 들어와서 약사에게 “가레가 끼고, 목이 따끔해요” 라고 말한다. 그러면, 약사는 `목감기약` 을 내어준다.

또 다른 손님은 “밥 먹고 체한 것 같아요” 라고 말한다. 그러면 약사는 `소화제` 를 내어줄 것이다.

`약국`이 바로 `팩토리` 이다. 특정 요구 조건에 따라, 그에 맞는 약을 내어주는 것과 같이 이런 상황에서 `팩토리 패턴`을 적용할 수 있는 것이다.

<br>

# ✔️ 팩토리 패턴 없이 만들어보기

<br>

위 예제 그대로 **팩토리 패턴 없이** 만들어보자. 우선, `약` 을 추상화한 인터페이스를 아래와 같이 만들었다.

```java
public interface Medicine {
    void get();
}
```

그리고, 이를 **상속 받아** 세부적인 약들을 정의해준다.

```java
public class Cold implements Medicine {

    @Override
    public void get() {
        System.out.println("감기약 드릴게요~");
    }
}
```

```java
public class Digestive implements Medicine {

    @Override
    public void get() {
        System.out.println("소화제 드릴게요~");
    }
}
```

```java
public class HeadachePill implements Medicine {

    @Override
    public void get() {
        System.out.println("두통약 드릴게요~");
    }
}
```

이제, 약국에서 **손님이 말한 증상에 따라 약을 처방**해보자.

```java
public class Pharmacy {
    public static void main(String[] args) {
        // 목이 아프다하면
        Medicine m1 = new Cold();
        m1.get();

        // 밥 먹고 체했다면
        Medicine m2 = new Digestive();
        m2.get();

        // 머리가 아프다면
        Medicine m3 = new HeadachePill();
        m3.get();
    }
}
```

```java
// 실행결과

감기약 드릴게요~
소화제 드릴게요~
두통약 드릴게요~
```

위와 같은 **결과**를 볼 수 있다. 해당 코드에서는 실제로 `new` 연산자를 이용해서 **객체를 직접 주입**해주고 있다. 한편, 주석으로 처리한 부분들은 실제로 `if-else` 문이 들어갈 것이다.

**만약**, 각각의 약들에 대한 **생성자 모양의 변경이 발생**한다면?? 실제 `new` 형태로 **주입하던 부분들을 모두 찾아** 생성자 모양에 따라 하나 하나씩 **모두 수정해주어야 할 것**이다.

또 만약, 약국에 **약사가 2명**이라고 가정해보자. 그러면, **조건 분기의 중복이 발생할 것**이다.

**(쉽게 말해, `main` 메소드와 동일한 `main2` 가 있는 셈이다)**

여기에 앞서 말한 생성자 수정 이슈가 추가로 발생하면 너무 번거로워지게 된다. 따라서, 이 코드의 문제점을 아래와 같이 정리할 수 있겠다.

<br>

### 🧐 문제점

- 메소드가 핵심 역할을 수행하는 책임과 더불어서 요구 조건을 검사하고 이에 따라 객체를 주입하는 책임까지 수행해야 한다
- 객체 주입을 `new` 형태로 주입했다면, **생성자 모양이 변경 될 때 해당 부분을 모두 찾아 직접 수정**해주어야 한다.
- 만약, 다른 곳에서 조건에 따라 객체를 주입하는 부분이 또 필요하다면 중복이 발생할 것이다.
- 객체를 `정적`으로 주입하고 있다 **(컴파일 시점에서 주입 될 객체가 모두 정해져 있다)**

<br>
이제 `팩토리 패턴`을 적용해보고 어떤 이점이 있는지 알아보자.

<br>

# ✅ 팩토리 패턴을 적용해보자

<br>

우선 **객체 생성을 위한 조건**에 해당하는 부분을 만들기 위해서 **상수 클래스 형태**의 `고객` 을 만들었다.

```java
public enum Customer {
    COLD("가레가 끼고, 목이 따끔해요"),
    DIGESTIVE("밥 먹고 체한 것 같아요"),
    HEADACHE("머리가 아파요");

    public String message;

    Customer(String message) {
        this.message = message;
    }
}
```

예시에 부합하는 상황을 만들기 위해서, 단일 상수 값이 아니라 `증상` 에 해당하는 `message` 변수까지 추가해주었다. 그리고, `객체 생성` 의 책임을 짊어질 `팩토리` 클래스를 만들어준다.

```java
public class MedicineFactory {

    public Medicine getMedicine(Customer customer) {
        return switch (customer) {
            case COLD -> new Cold();
            case DIGESTIVE -> new Digestive();
            case HEADACHE -> new HeadachePill();
        };
    }
}
```

이제, `팩토리`를 이용해서 객체를 주입해서 결과를 확인한다.

```java
public class Pharmacy {
    public static void main(String[] args) {
        MedicineFactory factory = new MedicineFactory();

        Medicine medicine = factory.getMedicine(Customer.COLD);
        medicine.get();

        medicine = factory.getMedicine(Customer.DIGESTIVE);
        medicine.get();

        medicine = factory.getMedicine(Customer.HEADACHE);
        medicine.get();
    }
}
```

```java
// 실행 결과

감기약 드릴게요~
소화제 드릴게요~
두통약 드릴게요~
```

**패턴을 적용해서 짠 코드**의 경우, 팩토리 클래스 자체를 주입하는 경우를 제외하고 `new` 형태로 객체를 주입 하는 부분이 없다.

`Medicine` 타입의 객체는 `MedicineFactory` 클래스를 이용해서 객체를 주입 받고 있다. 

그저 팩토리 객체를 만들어서 개체를 얻는 메소드와 증상에 대한 부분만 말해주었을 뿐이다.

**패턴 적용 전**의 상황과 마찬가지로, 객체 생성자 모양이 달라지게 되면 `팩토리` 클래스만 수정해주면 된다. 이제 패턴을 적용하고 바뀐 점을 정리해보자

<br>

### 🧐 패턴을 적용해서 이로운 점

- 개발자는 객체 주입은 `팩토리` 클래스에서 받고, 바로 핵심 로직을 개발할 수 있다
- 생성자 모양이 변경되면, 그저 `팩토리` 클래스 내부를 수정해주기만 하면 된다
- 다른 곳에서 객체 주입이 필요하면, `팩토리` 클래스 객체만 생성하여 주입 받으면 된다.
- **객체의 타입을 동적으로 관리** 할 수 있다??

<br>

### ❓ 객체의 타입을 동적으로 결정한다

<br>

**마지막 항목**을 조금 상세히 설명해보면, `컴파일 시점`에서 객체의 타입을 결정하는 것이 아닌 `런타임 시점`에서 타입을 결정할 수 있게 된다는 것이다.

**실제 프로그램이 실행된다고 가정**하고, 사용자로부터 `내 몸의 증상` 데이터를 입력 받는다고 해보자.
먼저, 기존 상수 클래스에서 입력 받은 값에 따라 타입을 반환하는 `create()`메소드를 추가해주었다.

```java
public enum Customer {
    COLD("가레가 끼고, 목이 따끔해요"),
    DIGESTIVE("밥 먹고 체한 것 같아요"),
    HEADACHE("머리가 아파요");

    public String message;

    Customer(String message) {
        this.message = message;
    }

    public static Customer create(String value) {
        for (Customer type : Customer.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }

        return null;
    }
}
```

그런 뒤, 자바의 `Sacanner` 클래스를 이용해 `증상`에 대한 데이터를 입력 받아 `약` 객체에 의존성을 주입해보자.

```java
public class Pharmacy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MedicineFactory factory = new MedicineFactory();
        String input = "";

        while (true) {
            input = scanner.nextLine();

            if (input.equals("-1")) {
                break;
            }

            Customer customer = Customer.create(input);
            Medicine medicine = factory.getMedicine(customer);
            medicine.get();
        }
    }
}
```

```java
// (실행 결과)
COLD
감기약 드릴게요~
DIGESTIVE
소화제 드릴게요~
HEADACHE
두통약 드릴게요~
```

위 코드의 `Medicine medicine = factory.getMedicine(customer);` 부분을 보면 `Medicine` 의 **세부 타입이 무엇인지 결정되지 않는다**.
그저, 추상화 객체 형태인 `Medicine` 으로 선언 되어 있다.

**그런데**, 프로그램을 실행해서 **증상을 얘기해주었더니** 그에 맞는 약들을 내어주었다. 이게 바로 `컴파일 시점(static)`에 객체 타입이 결정되는 것이 아니라, `런타임 시점(dynamic)`에 객체 타입이 결정된다는 말이다.

다음 내용으로는 이 `팩토리 패턴`을 **한 단계 더 추상화** 시킨 `추상 팩토리 패턴 (Abstract Factory Pattern)` 에 대해서 공부 해야겠다.

---

🔗 [https://ko.wikipedia.org/wiki/팩토리_메서드_패턴](https://ko.wikipedia.org/wiki/%ED%8C%A9%ED%86%A0%EB%A6%AC_%EB%A9%94%EC%84%9C%EB%93%9C_%ED%8C%A8%ED%84%B4)

🔗 [https://www.youtube.com/watch?v=q3_WXP9pPUQ](https://www.youtube.com/watch?v=q3_WXP9pPUQ)
