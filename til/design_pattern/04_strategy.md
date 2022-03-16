> **전략 패턴 (Strategy Pattern)**
> 
> 유사한 그룹 내에 속한 객체의 모양 또는 형태에 따라서, 각각 다르게 행동하도록 설계한 디자인 패턴이다. `유사 그룹`이라는 것은 예를 들어 `동물` 이라는 그룹에 속한 고양이, 강아지인 것과 같다.

<br>

# ✅ 설계 구조

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/89de8e2c-3ebc-4aed-a599-ec8322c2f2ce/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220316%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220316T032131Z&X-Amz-Expires=86400&X-Amz-Signature=2083ee125532fb775119eca448a3a12ba8b80edf4f8c0947fd10abf1121f845f&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`전략 패턴`의 기본적인 설계 구조는 위와 같다.  먼저 전략을 수행할 주체인 `Context` 가 있고, `전략`에 대해 **추상화 한 인터페이스 계층**인 `Strategy` 가 있다.

그리고, 이를 상속 받아서 `전략`에 대해 세부적으로 정의한 클래스들이 존재한다.

<br>

# ✅ 예시로 이해하기

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/666d14d5-be56-41c1-9e59-0ca51539ad16/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220316%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220316T032133Z&X-Amz-Expires=86400&X-Amz-Signature=135614c1cd3d5bbf6fef734b097aa0afb039c389d2b17ad72cb0421ae6af69c3&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

특정 포털 사이트에 위와 같이 사용자는 `검색 모드`를 설정할 수 있고, 설정한 모드에 따라서 돋보기 모양의 `검색` 아이콘을 클릭하면 검색이 이루어진다고 해보자.

`전체` 모드라면, 말 그대로 전체 콘텐츠에 대한 검색이 이루어져야 하며, `이미지` 모드라면 이미지 컨텐츠만 검색되어야 할 것이다.

이 예제에서 그룹은 `검색 방식` 이 될 것이고 그 그룹에 속한 개체들은 `전체`, `이미지`, `뉴스`, `지도` 등이 될 것이다.

즉, 유사한 그룹들의 **개체 형태에 따라서 각각 다른 검색 전략이 실행되어야 한다는 것**이다. 이런 경우에 `전략 패턴`을 사용할 수 있다.

<br>

# ✔️ 전략 패턴 없이 만들어보기

<br>

전략 패턴을 적용하지 않고 위 기능을 만들어 보자. 우선, 검색 형태 타입들을 상수 클래스인 `Enum` 으로 정의했다.

```java
public enum SearchType {
    ALL, IMAGE, NEWS, MAP
}
```

그리고 정의한 검색 타입을 필드 변수로 가지는 검색 버튼 클래스를 아래와 같이 정의하였다.

```java
public class SearchButton {
    private SearchType searchType;

    public SearchButton() {
        this.searchType = SearchType.ALL;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public void onclick() {
        if (this.searchType.equals(SearchType.ALL)) {
            System.out.println("모든 컨텐츠 검색");

            // do search `all`

        } else if (this.searchType.equals(SearchType.IMAGE)) {
            System.out.println("이미지 컨텐츠 검색");

            // do search `image`

        } else if (this.searchType.equals(SearchType.NEWS)) {
            System.out.println("뉴스 컨텐츠 검색");

            // do search `news`

        } else if (this.searchType.equals(SearchType.MAP)) {
            System.out.println("지도 컨텐츠 검색");

            // do search `map`
        }
    }
}
```

`디폴트 생성자`를 호출하면, 기본적으로 `검색 타입` 이 `전체` type 이 되도록 하였다. 그리고, 모드를 변경하는 경우를 고려하여 `setter` 메소드를 추가하였다. 이제 클라이언트에서 사용한다고 가정하고 실행 결과를 보자

```java
public class Client {
    public static void main(String[] args) {
        SearchButton searchButton = new SearchButton();
        searchButton.onclick();

        // 이미지 모드로 변경
        searchButton.setSearchType(SearchType.IMAGE);
        searchButton.onclick();

        // 뉴스 모드로 변경
        searchButton.setSearchType(SearchType.NEWS);
        searchButton.onclick();

        // 지도 모드로 변경
        searchButton.setSearchType(SearchType.MAP);
        searchButton.onclick();
    }
}
```

```
(실행결과)

모든 컨텐츠 검색
이미지 컨텐츠 검색
뉴스 컨텐츠 검색
지도 컨텐츠 검색
```

그런데 만약 `검색 모드`가 저기서 더 **추가**된다면 어떨까?? 그럼 아래와 같은 **수정 이슈가 발생**할 것이다.

<br>

---

- `SearchType` 에 추가된 모드 상수를 추가해야 한다
- `SearchButton` 클래스에 존재하는 `onclick()` 메소드 내에서 `조건절`을 추가하고 해당 블럭 내에서 추가된 검색 모드 로직을 넣어야 한다

---

<br>

**상수 클래스**에 타입을 추가하는 것은 물론 쉽지만, `onclick()` 메소드 내에 **조건절이 추가**되고 **로직 코드**까지 들어가면 점점 더 복잡해질 것이다. 
이렇게 되면, **가독성도 매우 떨어질 것**이다.

여기에 더해, 검색 모드가 수십 가지인데 `특정 모드`검색 로직을 수정하려고 한다면?? 해당 `조건절`이 어디인지도 찾아야 한다.

이 코드에 **문제점**은 아래와 같이 정리 할 수 있겠다.

<br>

### 🧐 문제점

- 모드가 증가함에 따라, `조건문`이 계속 들어가서 **가독성이 떨어진다**
- onclick() 메소드는 **검색을 해야 하는 책임**과, **타입을 검사하는 책임까지** 가지고 있다.
  **(OOP 원칙 위반)**
- 다양한 모드가 존재할 때, 특정 모드의 `검색 로직`을 **수정하려면** **해당 조건절을 찾아서 수정**해야 한다
- 모드의 개수 만큼 **검사해야 하는 조건문이 늘어남에 따라** `성능 이슈`가 발생할 수 있다.
- 코드가 정적이라고 할 수 있다. 즉, 컴파일 시점에서 모든 타입에 대한 상황을 결정지어서 짰기 때문이다.

<br>

이제, 이 문제점들을 기억해두고 `전략 패턴`을 적용해보자.

# ✅ 전략 패턴을 적용해보자

<br>

먼저, 맨 앞서 언급했던 구조대로 `전략을 추상화한 인터페이스`를 만들고 이를 상속 받아 전략에 대해서 상세하게 정의한 클래스를 만들어 준다.

```java
public interface SearchStrategy {
    void search();
}
```

```java
public class SearchAllStrategy implements SearchStrategy {
    @Override
    public void search() {
        System.out.println("모든 컨텐츠 검색 전략");
        // ...
    }
}
```

```java
public class SearchImageStrategy implements SearchStrategy {
    @Override
    public void search() {
        System.out.println("이미지 검색 전략");
        // ...
    }
}
```

```java
public class SearchMapStrategy implements SearchStrategy {
    @Override
    public void search() {
        System.out.println("지도 검색 전략");
        // ...
    }
}
```

```java
public class SearchNewsStrategy implements SearchStrategy {
    @Override
    public void search() {
        System.out.println("뉴스 검색 전략");
        // ...
    }
}
```

이제, `전략`을 수행할 주체인 `context` 클래스를 만든다. 해당 클래스에는 추상화 인터페이스 객체를 **필드 변수**로 가진다. 마찬가지로, `default` 모드는 `전체 검색` 이 되도록 `생성자`로 만들어주었다.

그리고, 모드 변경에 따라 `검색 전략`을 바꿀 수 있도록, `setter` 메서드를 추가해준다.

```java
public class SearchButton {
    private SearchStrategy searchStrategy;

    SearchButton() {
        this.searchStrategy = new SearchAllStrategy();
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void onclick() {
        searchStrategy.search();
    }
}
```

이제, 클라이언트에서 실행해보고 결과를 확인해보자

```java
public class Client {
    public static void main(String[] args) {
        SearchButton searchButton = new SearchButton();
        searchButton.onclick();

        // 이미지 모드로 변경
        searchButton.setSearchStrategy(new SearchImageStrategy());
        searchButton.onclick();

        // 뉴스 모드로 변경
        searchButton.setSearchStrategy(new SearchNewsStrategy());
        searchButton.onclick();

        // 지도 모드로 변경
        searchButton.setSearchStrategy(new SearchMapStrategy());
        searchButton.onclick();
    }
}
```

```java
(실행결과)

모든 컨텐츠 검색 전략
이미지 검색 전략
뉴스 검색 전략
지도 검색 전략
```

패턴을 적용하지 않을 때와 마찬가지로, 검색 모드가 계속 추가된다면 어떨까?? 나는 **추상화 계층을 상속 받아 전략을 새로 짜기만 하면 된다.** 그리고, 검색 모드가 바뀐 시점에서 해당 의존성을 주입해주기만 하면 된다.

`onclick()` 메소드 시점에서 보면, 본인은 그냥 `검색` 책임만 지면 되는 것이다. 이에 따라, 이전 문제점과 비교해서 정리해보면,

<br>

### 🧐 패턴을 적용해서 이로운 점

- 검색 모두가 추가되면, **전략을 새로 짜고 의존성만 주입**해주면 된다
- `onclick()` 메소드는 검색 로직을 수행하는 책임만 지면 된다. **( OOP → SRP 원칙 )**
- 특정 검색 모드의 **수정이 필요하면, 해당 전략 클래스만 수정**하면 된다.
- **모드에 따라 의존성이 바로 주입** 되므로 여러 조건절을 **검사할 필요가 없다.**
- **정적인 코드가 다소 개선**되었다.

<br>

마지막 항목에 대해서 조금 언급하자면, 현재 이 코드는 완전히 `dynamic` 하다 할 수 없다. 왜냐하면, 검색 모드에 따라 의존성을 내가 직접 주입하고 있기 때문이다 **(컴파일 시점에 객체 타입이 결정됨)**

여기서, `팩토리 패턴` 을 적용하면 `의존성 주입의 책임`을 **팩토리 클래스**에게 넘기기 때문에 `런타임 시점에 객체의 타입이 결정`되게 할 수 있다. 이렇게 되면 이 로직은 `dynamic` 하다고 할 수 있을 것이다. 해당 패턴에 대해서는 다른 페이지에 추가로 정리하겠다.

다시 돌아와서, `전략 패턴` 의 이로운 점 말고 **단점**은 무엇일까?? 만약에 검색 모드가 더 추가될 이슈가 없고, 2개 정도로 고정되는 로직이면 가독성 측면에서 조건 분기를 타는 게 좋은 것 같다.

**검색 모드**가 2개 뿐인데, 굳이 추상화 인터페이스를 만들고 이를 상속 받아 전략을 만들고까지 할 필요까지 있는지 말이다. 그래도, 나는 **패턴을 적용해서 사용**할 것 같다. `추상화`를 통해 얻는 이점이 더 많다고 생각하기 때문이다. **( 테스트 코드 작성 및 OOP 원칙 등 )**

---

🔗 [https://www.youtube.com/watch?v=lJES5TQTTWE](https://www.youtube.com/watch?v=lJES5TQTTWE)

🔗 [https://velog.io/@y_dragonrise/디자인-패턴-전략-패턴Strategy-Pattern](https://velog.io/@y_dragonrise/%EB%94%94%EC%9E%90%EC%9D%B8-%ED%8C%A8%ED%84%B4-%EC%A0%84%EB%9E%B5-%ED%8C%A8%ED%84%B4Strategy-Pattern)

🔗 [https://velog.io/@kyle/디자인-패턴-전략패턴이란#전략패턴의-장점](https://velog.io/@kyle/%EB%94%94%EC%9E%90%EC%9D%B8-%ED%8C%A8%ED%84%B4-%EC%A0%84%EB%9E%B5%ED%8C%A8%ED%84%B4%EC%9D%B4%EB%9E%80#%EC%A0%84%EB%9E%B5%ED%8C%A8%ED%84%B4%EC%9D%98-%EC%9E%A5%EC%A0%90)

🔗 [https://ko.wikipedia.org/wiki/전략_패턴](https://ko.wikipedia.org/wiki/%EC%A0%84%EB%9E%B5_%ED%8C%A8%ED%84%B4)
