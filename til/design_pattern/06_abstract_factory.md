##:six: Abstract Factory Pattern

> - **추상 팩토리 패턴 (Abstract Factory Pattern)**
> 
> `팩토리 패턴`에서 한 단계 더 추상화를 적용하여 객체를 생성하는 공장을 여러 개로 두고, 특정 조건에 따라 알맞은 공장에 객체를 얻을 수 있도록 설계한 원칙이다.

<br>

# ✅ 설계 구조

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/6f9c4f05-6580-4783-abb9-6664797f05ab/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220321%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220321T095517Z&X-Amz-Expires=86400&X-Amz-Signature=384424ebbebc875e63cf2682dd87ba9758c66f8f698db2aaf1d1522119d143f1&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

구조를 보면 `팩토리` 개념을 추상화한 인터페이스 계층의 `AbstractFactory` 가 있고, 이를 상속 받아 각각 세부적인 `팩토리` 개념을 정의한 클래스가 있다. 이제 `Client` 에서 객체를 주입 받아야 할 때 상황에 맞는 `팩토리` 에서 객체를 제공 받는 형태이다.

# ✅ 예시로 이해하기

바로 이전 글인 `팩토리 메소드 패턴`에서 사용했던 예시를 그대로 가져오겠다. 링크를 타기 귀찮으니 상황을 가져오면 약국에 감기, 두통, 소화제 약이 있고 손님이 말한 증상에 따라 약을 준다고 가정한다. 여기서 각 약들의 **제조사**는 `Foo`, `Bar` 이 **두 개가 존재**한다고 가정한다. 그럼 감기에 걸린 손님은 `Foo` 사 감기약을 선택할 수도 있고, `Bar` 사 감기약을 선택할 수도 있다.

<br>

# ✔️ 팩토리에 추상화를 적용하지 않는다면?

<br>

아래 코드는 기존 `팩토리 패턴`의 **감기약** 코드다.

```java
public class Cold implements Medicine {

    @Override
    public void get() {
        System.out.println("감기약 드릴게요~");
    }
}
```

이 코드에서 감기약의 종류를 따진다면 아래처럼 조건문이 들어갈 것이다.

```java
public class Cold implements Medicine {

    @Override
    public void get() {
        if (Foo 회사꺼 주세요) {
                System.out.println("Foo 감기약 드릴게요~");
        } else {
                System.out.println("Bar 감기약 드릴게요~");
        }
    }
}
```

약의 종류가 많다면 약 별로 **조건문을 다 넣을 것인가?** 여기에 더해, `감기약` 클래스에서 각 약품의 효능 또는 구성 성분을 출력하는 메소드가 추가된다면? **메소드마다 조건 분기를 다 넣을 것인가??**

<br>

# ✅ 추상화 팩토리 패턴을 적용해보자

<br>

우선 기존에 감기, 두통, 소화제 클래스가 각 1개씩 존재하던 것을 `Foo` 사와 `Bar` 사 이 두 분류의 제조사 약으로 분류해 새로 만들어주었다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9f78cbe3-7d51-4220-aabd-fc09f2e86a38/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220321%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220321T095657Z&X-Amz-Expires=86400&X-Amz-Signature=cb7fb8cfdf67fb201f219f6749e41ded0c1420cfe5b220300bffd0bb0e89b42c&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

```java
public class FooCold implements Medicine {

    @Override
    public void get() {
        System.out.println("Foo 사 감기약 드릴게요");
    }
}
```

```java
public class BarCold implements Medicine {

    @Override
    public void get() {
        System.out.println("Bar 사 감기약 드릴게요");
    }
}
```

...

그리고, `팩토리`를 **추상화**한 인터페이스 계층을 아래와 같이 만들고 이를 `Foo` 사 공장과 `Bar` 사 공장을 만든다.

```java
public interface AbstractMedicineFactory {
    Medicine getMedicine(Customer customer);
}
```

```java
public class FooMedicineFactory implements AbstractMedicineFactory{

    public Medicine getMedicine(Customer customer) {
        return switch (customer) {
            case COLD -> new FooCold();
            case DIGESTIVE -> new FooDigestive();
            case HEADACHE -> new FooHeadachePill();
        };
    }
}
```

```java
public class BarMedicineFactory implements AbstractMedicineFactory{

    public Medicine getMedicine(Customer customer) {
        return switch (customer) {
            case COLD -> new BarCold();
            case DIGESTIVE -> new BarDigestive();
            case HEADACHE -> new BarHeadachePill();
        };
    }
}
```

이제, 약국에서 두 제조사 제품 유형으로 각각 다른 객체를 주입 받아보자.

```java
public class Pharmacy {
    public static void main(String[] args) {
        // Foo 사 약 제공
        AbstractMedicineFactory factory = new FooMedicineFactory();

        Medicine medicine = factory.getMedicine(Customer.COLD);
        medicine.get();

        medicine = factory.getMedicine(Customer.DIGESTIVE);
        medicine.get();

        medicine = factory.getMedicine(Customer.HEADACHE);
        medicine.get();

        // Bar 사 약 제공
        factory = new BarMedicineFactory();

        medicine = factory.getMedicine(Customer.COLD);
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
Foo 사 감기약 드릴게요
Foo 사 소화제 드릴게요~
Foo 사 두통약 드릴게요~
Bar 사 감기약 드릴게요
Bar 사 소화제 드릴게요~
Bar 사 두통약 드릴게요~
```

### 🧐 패턴을 적용해서 이로운 점

- `단일 팩토리`에서 객체가 여러 유형으로 분리해야 된다면, 단지 분류되는 객체 클래스만 따로 만들어 주면 된다
- 기존 `팩토리 메소드 패턴` 상황에서 여러 조건 분기를 넣어줄 필요가 없다. 단지, **필요한 공장 의존성을 주입**해주기만 하면 된다.
- **객체 타입**을 보다 동적으로 결정하고 관리할 수 있다.

**하지만**, `제조사` 개념이 추가 됨에 따라 기존 약 관련 클래스가 **3개**에서 **6개**로 늘어났다. 제조사가 늘어나면 늘어날 수록, `약` 클래스가 더 늘어나게 될 것이다. 약의 종류가 추가되면 그 만큼 많은 클래스가 필요해질 것이다.

---

🔗 [https://youtu.be/q3_WXP9pPUQ?t=543](https://youtu.be/q3_WXP9pPUQ?t=543)

🔗 [https://ko.wikipedia.org/wiki/추상_팩토리_패턴](https://ko.wikipedia.org/wiki/%EC%B6%94%EC%83%81_%ED%8C%A9%ED%86%A0%EB%A6%AC_%ED%8C%A8%ED%84%B4)
