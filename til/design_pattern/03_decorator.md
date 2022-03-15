# Decorator Pattern

> <b>데코레이터 패턴 (Decorator Pattern)</b>
> 
> 데코레이터는 `장식` 이라는 의미로 특정 객체가 기본적으로 수행하는 역할에 더해서 추가적인 기능을 동적으로 덧붙일 때 사용한다.

# ✅ 설계 구조

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/2cf18c7b-767c-4449-92b6-47a310b69113/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220315%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220315T024651Z&X-Amz-Expires=86400&X-Amz-Signature=bba0ab4ac8a56b891abd18f813774983e8c5a8492c2533e224a4a3b9ec788066&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`데코레이터 패턴`의 설계 구조는 위와 같다. 역할에 대해 추상화 인터페이스 형태의 `Component` 가 있고, 이를 상속 받아 **역할을 세부적으로 구현**한 `ConcreteComponent` 가 있다.

이때, 기본 형태에서 `장식`을 추가할 수 있도록 **추상 클래스 형태**로 `Decorator` 가 추가 된다. 해당 클래스에서 **메인 행위에 대한 인터페이스를 상속 받되**, 실질적인 추가 기능에 대한 정의를 하지 않고 추상 클래스로 남겨 두는 것이다.

이제, 해당 추상 클래스인 `Decorator` 를 상속 받아 실제로 어떠한 `장식`을 추가 할지 명시하는 구현체 `~A`, `~B` 가 있다.

# ✅ 예시로 이해하기

<br>

**이해하기 쉬운 예**로, `전투기 조종 게임`을 한다고 가정해보자. 전투기를 조종하는 `사용자`는 기본적으로 `기본 전투기`를 사용할 수 있으며, 해당 전투기는 **공격 버튼을 누를 시 기본 탄환이 발사**된다.

게임을 진행하면서, 유저는 `공격 아이템`을 획득 할 수 있다. 아이템은 `레이저`, `플라즈마` 이 두 가지가 있다고 가정한다. 아이템을 획득 할 때마다, **공격 시 기본 탄환과 더불어 획득한 아이템 무기가 발사되어야 한다.**

이처럼 객체가 기본적으로 수행하는 역할에 **더불어** 어떠한 `추가 기능`이 추가되어야 할 때 `데코레이터 패턴`을 사용할 수 있다.

그림과 글로만 봐서는 이해가 힘들어서 이제 실제 코드로 적용해보자. 먼저 `Decorator` 를 사용하지 않고 만들어 본 후에 적용해보도록 하자.

# ❓ 데코레이터 패턴 없이 사용해보기

<br>

만약에 위의 예제인 `전투기 조종` 게임을 **데코레이터** **패턴 없이** 만든다고 해보자. 기본 공격에 더불어서 유저가 아이템을 획득 한 부분에 대해서 공격 행위가 추가 되어야 할 것이다.

먼저 전투기를 추상화 한 인터페이스 `Fighter` 를 만든다

```java
public interface Fighter {
    void attack();
}
```

그리고, 기본 전투기를 만들면 아래와 같이 될 것이다.

```java
public class BasicFighter implements Fighter {

    @Override
    public void attack() {
        System.out.println("탄환 발사");
    }
}
```

만약, 레이저 아이템을 획득했다면 기본 공격과 레이저까지 발사되는 전투기가 필요할 것이다.

```java
public class LaserFighter implements Fighter {

    @Override
    public void attack() {
        System.out.println("탄환 발사");
        System.out.println("레이저 발사");
    }
}
```

또, **플라즈마** 아이템을 획득했을 때와 **레이저와 플라즈마** 둘 다 획득 했을 때도 고려해야 할 것이다.

```java
public class PlasmaFighter implements Fighter {

    @Override
    public void attack() {
        System.out.println("탄환 발사");
        System.out.println("플라즈마 발사");
    }
}
```

```java
public class LaserPlasmaFighter implements Fighter {

    @Override
    public void attack() {
        System.out.println("탄환 발사");
        System.out.println("레이저 발사");
        System.out.println("플라즈마 발사");
    }
}
```

이렇게 만들어 두고 클라이언트에서 사용해보자

```java
public class Pilot {

    public static void main(String[] args) {
        Fighter fighter = new BasicFighter();
        fighter.attack();
        System.out.println("");

        fighter = new LaserFighter();
        fighter.attack();
        System.out.println("");

        fighter = new PlasmaFighter();
        fighter.attack();
        System.out.println("");

        fighter = new LaserPlasmaFighter();
        fighter.attack();
    }
}
```

```java
탄환 발사

탄환 발사
레이저 발사

탄환 발사
플라즈마 발사

탄환 발사
레이저 발사
플라즈마 발사
```

따라서, `데코레이터를 사용하지 않은 구조`는 아래와 같은 형태를 이룬다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/437ea3d2-ff4a-47bb-ac9f-2b3d8a8a38b5/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220315%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220315T024816Z&X-Amz-Expires=86400&X-Amz-Signature=8249c3486f3a6eaf8e4e65f46e012dba8613331dccc075955285b7143ce86c4a&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

어떤가?? **아이템이 고작 2개일 뿐인데도 4가지 경우를 구현**해야 한다. 여기서 아이템이 1개 추가로 업데이트 된다면, 만들어야 하는 경우의 수가 **기하급수적**으로 늘어난다. 또한, 여러 경우들을 검사해서 특정 경우와 일치하는 클래스 객체를 주입해주어야 한다.

심지어, 획득한 아이템의 `order` 까지 적용된다면??

**1. 레이저를 먼저 획득하고 플라즈마를 획득한 경우**

**2. 플라즈마를 먼저 획득하고 레이저를 획득한 경우**

이 부분 까지 고려해서 만들어야 한다. **너~~무 번거로운 일**이다. 이제 `데코레이터 패턴`을 적용하는 예시를 보자.

# ✅ 데코레이터 패턴을 적용해보자

<br>

앞서 정의한 `Fighter` 인터페이스와 그 구현체인 기본 전투기 `BasicFighter` 는 그대로 두고, `Decorator` 를 적용할 **추상 클래스**를 만든다.

```java
public abstract class FighterDecorator implements Fighter {
    private Fighter decoratedFighter;

    public FighterDecorator(Fighter decoratedFighter) {
        this.decoratedFighter = decoratedFighter;
    }

    @Override
    public void attack() {
        decoratedFighter.attack();
    }
}
```

이때, **클래스에 상위 인터페이스를 필드 변수**로 둔다. 그리고, 해당 변수에 `의존성`을 주입하기 위한 생성자를 추가해준다

이 클래스가 `추상클래스` 인 이유는, 전투기의 어떤 `장식`을 추가 할 것인지 **정의하는 부분이 아니기 때문**이다.

이제, 전투기에 추가할 `장식`인 레이저와 플라즈마 데코레이터를 만들어보자.

```java
public class LaserDecorator extends FighterDecorator {
    public LaserDecorator(Fighter decoratedFighter) {
        super(decoratedFighter);
    }

    @Override
    public void attack() {
        super.attack();
        System.out.println("레이저 발사");
    }
}
```

```java
public class PlasmaDecorator extends FighterDecorator {
    public PlasmaDecorator(Fighter decoratedFighter) {
        super(decoratedFighter);
    }

    @Override
    public void attack() {
        super.attack();
        System.out.println("플라즈마 발사");
    }
}
```

이제 모든 경우에 대해서 클라이언트에서 사용해보자

```java
public class Pilot {

    public static void main(String[] args) {
        // 기본 전투기
        Fighter fighter = new BasicFighter();
        fighter.attack();

        System.out.println("");

        // 레이저만 획득
        fighter = new LaserDecorator(new BasicFighter());
        fighter.attack();
        
        System.out.println("");
        
        // 플라즈마만 획득
        fighter = new PlasmaDecorator(new BasicFighter());
        fighter.attack();
        
        System.out.println("");

        // 둘다 획득
        fighter = new PlasmaDecorator(new LaserDecorator(new BasicFighter()));
        fighter.attack();
    }
}
```

```
탄환 발사

탄환 발사
레이저 발사

탄환 발사
플라즈마 발사

탄환 발사
레이저 발사
플라즈마 발사
```

`Decorator` 를 사용할 때와 하지 않을 때 모두를 적용해보았다. 확실히 데코레이터 패턴을 적용하면, **획득한 시점에서 해당 아이템 객체를 추가로 주입해주기만** 했더니, 알아서 획득한 아이템의 공격 액션이 추가되었다. 시점에 따라 주입하기에 아이템 획득 순서를 고려할 필요도 없다.

적용하지 않을 때는 모든 경우를 만들어야 했고, 해당 경우가 어떤 경우 인지에 따라 알맞은 객체를 넣어야 하는 반면, 패턴을 적용한 경우는 단순히 획득한 아이템 객체만 주입해주기만 하면 된다.

# ✅ 장점과 단점

## 👍 장점

- 추가할 행동들에 대한 **모든 경우를 따질 필요가 없다**
- **어떠한 경우에 대한 것**인지 검증할 필요 또한 없다
- 따라서, **동적**으로 상황에 맞게 객체에 대한 기능을 추가할 수 있다
- 아이템 획득에 `순서` 가 보장되어야 하는 상황일 때, 패턴을 적용하면 이를 고려할 필요가 없다. 

    (**단지, 획득 시점에 의존성만 주입해주면 된다)**

## 👎 단점

- `Decorator` 가 많이 필요한 경우, 해당 클래스가 계속 추가되어 복잡해질 수 있다
- 사용 시, 여러 객체들이 **단계적으로 감싸져 있는 형태**라서 가독성이 좋지 않다

```
--> new A(new B(new C())) ...
```

---

🔗 [https://www.youtube.com/watch?v=q3_WXP9pPUQ](https://www.youtube.com/watch?v=q3_WXP9pPUQ)

🔗 [https://ko.wikipedia.org/wiki/데코레이터_패턴](https://ko.wikipedia.org/wiki/%EB%8D%B0%EC%BD%94%EB%A0%88%EC%9D%B4%ED%84%B0_%ED%8C%A8%ED%84%B4)

🔗 [https://coding-factory.tistory.com/713](https://coding-factory.tistory.com/713)
