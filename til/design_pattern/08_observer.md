# :eight: Observer Pattern

> **# 옵저버 패턴 (Observer Pattern)**
>
> - 한 객체에 상태 변경이 일어났을 때, 이 객체의 상태에 영향을 받는 여러 객체들에게 상태가 바뀌었음을 알려줌으로서 이에 맞는 액션을 취할 수 있도록 설계된 패턴

<br>

# ✅ 설계 구조

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/641048c7-713b-49ae-ac13-93af7f445a60/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220323%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220323T060608Z&X-Amz-Expires=86400&X-Amz-Signature=1c30e8fec13c04f69c130a3acef160412bfcf1dc93526cd6b2fe2f14c89e9900&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

설계 구조는 위와 같다. `Subject` 는 행위의 주체이다. 즉, 이 객체의 행위에 따라서 `Observer` 들에게 알림이 전달되는 것이다. 기본적으로 `Observer` 를 `등록`, `삭제`, `알림` 이 3가지 액션을 취하도록 인터페이스가 구성되어 있다.

나는 이 **3가지 행위**를 인터페이스 단에서 **반드시 선언할 필요는 없다**. 옵저버를 **등록**하고 **삭제**하는 부분에 대해서는 `Subject` 를 상속 받는 클래스에서 `setter` 등의 메소드로 따로 구현해도 괜찮아 보이기 때문이다.

**하지만,** 테스트 코드에서 `가짜 역할`을 하는 객체를 만들어야 할 수도 있다는 점을 고려하면, 인터페이스 단에서 선언하는 것도 좋다고 생각한다.

**(단지 선택의 문제인 것 같다. 이런 의문까지 모두 생각하면 끝이 없기에 여기서 끝내야겠다)**

# ✅ 예시로 알아보기

가장 흔하게 접할 수 있는 예시는 `유튜브` 이다. A 채널을 구독한 나는, 해당 채널에 새로운 동영상이 업로드 되거나 커뮤니티에 글이 등록되면 알림을 받을 수 있다. 구독이 되어 있기 때문이다.

또, 네이버 지식 백과에서 재밌는 예제가 있어서 가져와 보았다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/5c5a2322-c26c-457f-be2f-04353e3641a7/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220323%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220323T060623Z&X-Amz-Expires=86400&X-Amz-Signature=5c9e4b7cf65bcb4bb1121b4ee5377befff713df32386b7cdbbc8e62c78159f1c&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

퇴근을 한 아빠의 기분을 엄마가 파악하고 기분이 좋은지 나쁜지 자식들에게 알려준다. 아들은 게임을 하다 가도 아빠가 기분이 좋지 않으면, 책상에 앉아 공부를 한다. 딸도 마찬가지로 아빠의 기분이 좋지 않으면 설거지를 한다고 한다.

아이들은 엄마로부터 아빠의 기분이 `좋다`, `나쁘다` 만 듣고서 행동을 다르게 한다는 것이다. 이때, 엄마는 `Subject` 개념에 속하며 아이들은 `Observer` 계층에 속한다고 볼 수 있다.

이처럼, 한 `객체의 상태` (**아빠의 기분**)에 따라 이에 `의존하고 있는 객체`들 **(자식들)** 에게 `행위의 주체` (**엄마가 기분 상태를 알려줌)** 가 알려줌으로서 그에 맞는 액션을 취할 수 있도록 할 때 `옵저버 패턴`을 사용할 수 있다.

<br>

# ✔️ 패턴 없이 코딩해보기

<br>

바로 위에 **예제**를 가져와서 코딩을 해보았다. 먼저, 아들과 딸만 있다고 가정하고 아빠의 기분에 따라 행동에 대해 정의를 해주었다.

```java
public class Son {
    public void act(boolean isAngry) {
        if (isAngry) {
            System.out.println("아들 : 공부 모드로 전환!");
        } else {
            System.out.println("아들 : 롤 한판 해볼까");
        }
    }
}
```

```java
public class Daughter {
    public void act(boolean isAngry) {
        if (isAngry) {
            System.out.println("딸 : 설거지 모드로 전환!");
        } else {
            System.out.println("딸 : 방탄 오빠들 봐야지");
        }
    }
}
```

그리고, **엄마**가 자식들에게 아빠의 기분을 아래와 같이 알려주었다.

```java
public class Mother {
    public static void main(String[] args) {
        Son son = new Son();
        Daughter daughter = new Daughter();

        System.out.println("# 아빠가 화가 났다");
        son.act(true);
        daughter.act(true);

        System.out.println("\n# 아빠가 기분이 좋다");
        son.act(false);
        daughter.act(false);
    }
}
```

```java
// 실행 결과
# 아빠가 화가 났다
아들 : 공부 모드로 전환!
딸 : 설거지 모드로 전환!

# 아빠가 기분이 좋다
아들 : 롤 한판 해볼까
딸 : 방탄 오빠들 봐야지
```

만약 **강아지 객체가 추가** 된다고 해보자. 그렇다면, `Mother` 클래스에서 해당 객체를 생성해주기 위한 코드가 추가된다.

그리고 추가된 객체에게 **상태 변화를 알려주기 위한 코드가 추가**될 것이다. 이렇듯 의존하고 있는 코드가 많아질 수록, 상태 변화를 알려주는 코드가 점점 더 늘어나서 비효율적이다.

그리고 무엇보다 `Mother` 클래스는 알림을 전송해주어야 할 객체가 `Son`, `Datughter` 등으로 구체화되어 있기 때문에 **서로 강하게 결합**되어 있다고 할 수 있다.

**(물론, 이 일이 실제 상황으로 생각한다면 이상한 점이 아니다)**

강하게 결합이 되어있는 모양이다 보니, **요소가 추가**될 수록 이에 맞게 `유연하게 바꿀 수 없다는 점`과 **알림을 받는 객체의** **수정이 발생**한 경우 `Mother` **클래스에도 영향을 끼칠 수 있다는 것**이 큰 문제이다.

<br>

# ✅ 옵저버 패턴을 적용해보자

<br>

패턴을 적용하기 위해 인터페이스를 2개 만들 것이다. 먼저, 행위 주체자인 `Subject` 와 알림을 받을 `Observer` 이 두 개이다.

```java
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
```

```java
public interface Observer {
    void update(boolean isAngry); // 이는 패턴 없이 만들 때의 act() 역할을 담당할 것이다
}
```

먼저, `Observer`의 구현체들을 만들어 준다.

```java
public class Son implements Observer {

    @Override
    public void update(boolean isAngry) {
        if (isAngry) {
            System.out.println("아들 : 공부 모드로 전환!");
        } else {
            System.out.println("아들 : 롤 한판 해볼까");
        }
    }
}
```

```java
public class Daughter implements Observer {

    @Override
    public void update(boolean isAngry) {
        if (isAngry) {
            System.out.println("딸 : 설거지 모드로 전환!");
        } else {
            System.out.println("딸 : 방탄 오빠들 봐야지");
        }
    }
}
```

그리고, `Subject` 구현체인 `MotherSubject` 를 구현해준다. 이 클래스의 역할은, 아빠의 기분을 자식들에게 알려주는 역할을 담당한다. 그리고, 각 `Observer` 객체를 리스트 형태로 가지고 있는 필드 변수를 추가해준다.

```java
public class MotherSubject implements Subject {
    List<Observer> observers;
    private boolean isAngry;

    MotherSubject() {
        this.observers = new ArrayList<>();
    }

    MotherSubject(boolean isAngry) {
        this.isAngry = isAngry;
    }

    public void setAngry(boolean angry) {
        isAngry = angry;
    }

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (this.observers.size() != 0) {
            this.observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer listener : observers) {
            listener.update(isAngry);
        }
    }
}
```

이제, 패턴을 적용한 코드를 아래와 같이 사용할 수 있겠다.

```java
public class Mother {

    public static void main(String[] args) {
        MotherSubject subject = new MotherSubject();

        subject.registerObserver(new Son());
        subject.registerObserver(new Daughter());
        subject.registerObserver(new Pomeranian());

        System.out.println("# 아빠가 화가 났다");
        subject.setAngry(true);
        subject.notifyObservers();

        System.out.println("\n# 아빠가 기분이 좋다");
        subject.setAngry(false);
        subject.notifyObservers();
    }
}
```

```java
// 실행 결과
# 아빠가 화가 났다
아들 : 공부 모드로 전환!
딸 : 설거지 모드로 전환!
포메 : 똥 오줌을 참고 애교를 발사한다

# 아빠가 기분이 좋다
아들 : 롤 한판 해볼까
딸 : 방탄 오빠들 봐야지
포메 : 똥을 싼다
```

여기서 보면, `포메라니안` 이라는 `Observer` 주체를 추가한 모습을 볼 수 있다. 나는 단지, 이 객체를 `등록` 해주었을 뿐 그 어떠한 코드도 `Mother` 에서 추가된 것은 없다는 것을 알 수 있다.

`Mother` 클래스에서만 보면, `Observer` 형의 리스트 변수를 가지고 있을 뿐 그 객체가 명확하게 `Son` 인지, `Daughter` 인지는 모른다. 이 말은, 앞서 패턴을 적용한 부분과는 다르게 `느슨한 결합` 관계를 형성하고 있다는 것을 알 수 있다.

이렇게 되면, `Observer` 의 어떤 수정 이슈가 발생할 때 해당 클래스만 수정해주면 되며 `Mother` 클래스에 끼치는 **영향이 작다**는 말이다.

`옵저버 패턴` 에 대해 다시 정리해보면, `Subject` 계층과 `Observer` 계층은 서로 연관을 가지고 있지만, 서로 누구인지는 알지 못하는 `느슨한 결합` 형태를 유지하는 패턴이며 1개의 `Subject` 에 N 개의 `Observer` 관계가 형성되는 것을 알 수 있다. 이에 따라, `Subject` 가 상태의 변화를 각 `Observer` 에게 알림으로서, 해당 상태에 맞는 액션이 실행되는 패턴이라고 할 수 있다.

<br>

---

🔗 [https://terms.naver.com/entry.naver?docId=3532972&cid=58528&categoryId=58528](https://terms.naver.com/entry.naver?docId=3532972&cid=58528&categoryId=58528)

🔗 [https://www.crocus.co.kr/1530](https://www.crocus.co.kr/1530)

🔗 [https://velog.io/@hanna2100/디자인패턴-2.-옵저버-패턴-개념과-예제-observer-pattern](https://velog.io/@hanna2100/%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%A8%ED%84%B4-2.-%EC%98%B5%EC%A0%80%EB%B2%84-%ED%8C%A8%ED%84%B4-%EA%B0%9C%EB%85%90%EA%B3%BC-%EC%98%88%EC%A0%9C-observer-pattern)
