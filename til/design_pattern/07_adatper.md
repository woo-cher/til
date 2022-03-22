# :seven: Adapter Pattern

> **# 어댑터 패턴 (Adapter Pattern)**
> 
> - 형식이 서로 다른 두 요소 사이에서, 이 두 요소가 상호 호환이 이루어 질 수 있게 해주는 것

<br>

# ✅ 설계 구조

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/d82aa707-c962-4168-89d8-63d2e3f345b0/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220322%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220322T031939Z&X-Amz-Expires=86400&X-Amz-Signature=ce033bcaf75753c3b9ffbfc8f55d3035156898129f944d725a230565254178a5&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`어댑터 패턴` 의 설계 구조는 위와 같다. 먼저, `Target` 부분은 기존에 우리가 사용하던 환경이라고 생각하면 된다. 그리고 `Adaptee` 는 우리가 가져와서 호환해야 하는 로직이다. 이 로직을 우리 환경에 맞게 사용하기 위해 `Adapter` 라는 클래스를 만들어주는 것이다.

<br>

# ✅ 예시로 알아보기

<br>

보통 어댑터 패턴이 사용되는 경우는 기존 서비스 로직에서 **인터페이스 형식이 다른 객체를 가져와 동일하게 실행해야 하는 경우**에 사용한다. **가장 쉬운 예**로, 우리나라의 전기 공급 방식은 `220v` 를 지원하는 콘센트가 필요한 반면, 일본의 경우 `110v` 콘센트가 있어야 지만 전력 공급을 받을 수 있다.

**그래서**, 일명 `돼지코` 라고 불리는 어댑터를 통해서 콘센트를 연결해주면 `110V` 형태 콘센트에도 정상적으로 전력을 공급 받을 수 있게 된다.

실습을 위한 예제로는 [전략 패턴 글](./04_strategy.md) 사용한 예제를 그대로 가져오겠다.

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/e0b3d9d1-a3e1-4823-912b-ff8e10a3ebda/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220322%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220322T031345Z&X-Amz-Expires=86400&X-Amz-Signature=b1c14d2fdc8375b20553291a13eaaf593502931287d4506c3d19d1f92f942f32&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

기존 **검색 서비스 UI 전략**에서 `동영상` 컨텐츠를 검색하는 전략이 추가되었다. **하지만** 동영상을 검색하는 **비즈니스 로직은 타사에서 짠 코드**여서 `인터페이스`가 다르다고 가정한다.

기존 우리 회사가 만든 검색 전략 `인터페이스`는 아래와 같고, 해당 전략 인터페이스를 각 상속 받아 `검색 전략`을 `전체`, `이미지`, `뉴스`, `지도` 검색 등으로 구현했었다.

```java
public interface SearchStrategy {
    void search();
}
```

그런데, `동영상` 검색의 경우 **타사**에서 만들었기에 인터페이스가 아래와 같았다.

```java
public interface FindStrategy {
    void find(boolean global);
}
```

아래는 **전략을 수행하기 위한** 기존 검색 버튼의 클래스이다.

```java
public class SearchButton {
    private SearchStrategy searchStrategy;

    public SearchButton() {
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

필드 변수로 **우리가 만든** `SearchStrategy` 객체를 가지고 있다. **그렇기 때문에**, 타사에서 만든 `동영상 검색` 전략 객체 의존성을 주입해줄 수가 없다.

만약에 **어댑터 패턴 없이** 해당 전략을 사용하려면, 위 클래스 수정이 필요할 것이다. 먼저, **타사에서 만든 인터페이스 전략 필드가 추가**될 것이고, **onclick() 메소드에서 어떤 조건 분기가 추가** 될 것이다.

예를 들면 아래와 같이 말이다.

```java
/**
 *  만약에 새로 추가된 `동영상 검색` 로직을 추가해야 한다면??
 */
public class SearchButton {
    private SearchStrategy searchStrategy;
    private FindStrategy findStrategy; // 타사 인터페이스 필드가 추가되어야 한다

    public SearchButton() {
        this.searchStrategy = new SearchAllStrategy();
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    // 타사 인터페이스 의존성 주입을 위한 생성자가 필요하다
    public void setFindStrategy(FindStrategy findStrategy) {
        this.findStrategy = findStrategy;
    }

    // onclick 에서 동영상 검색의 경우 타사 전략을 실행해야 한다
    public void onclick(Class clazz) {
        if (clazz.equals(SearchStrategy.class)) {
            searchStrategy.search();
        } else {
            findStrategy.find(true);
        }
    }
}
```

이런 형태에서, 클라이언트에서 사용해보자

```java
public class Client {
    public static void main(String[] args) {

        // 우리 회사에서 만든 검색 전략
        SearchButton searchButton = new SearchButton();
        searchButton.onclick(SearchStrategy.class);

        searchButton.setSearchStrategy(new SearchImageStrategy());
        searchButton.onclick(SearchStrategy.class);

        // 타 회사에서 만든 동영상 검색 전략
        searchButton.setFindStrategy(new FindVideoStrategy());
        searchButton.onclick(FindVideoStrategy.class);
    }
}
```

기존에 `onclick()` 만 호출 했던 것에서, 조건을 따지기 위해 요구 사항이 매개 변수로 추가되었다. 이 상태에서 만약 또 다른 회사의 검색 전략이 들어가면 너무 번거로워 질 것이다.

그리고, 상위 계층의 `인터페이스`가 나누어져 있기 때문에 검색을 위해 어떤 매개 변수가 추가되거나 하면 **자사**에서 만든 **서브 클래스**들과 **타사**의 **서브 클래스**들을 **각기 따로 수정**해주어야 하는 번거로움이 발생한다.

<br>

### 🧐 문제점

<br>

- 인터페이스가 나누어져서 수정 이슈 발생 시 각 서브 클래스들을 따로 모두 수정해주어야 한다
- 타사 검색 전략이 추가될 수록 **번거로운 작업이 배가 된다**
- 전략을 사용하는 코드에서 **조건 검사를 위해 추가 작업이 발생**한다

<br>

# ✅ 어댑터 패턴을 적용해보자

<br>

`동영상` 검색 인터페이스를 호환하기 위한 어댑터를 만든다. 이 어댑터는 타사에서 만든 인터페이스를 필드로 가지고 있다.

```java
public class SearchFindAdapter implements SearchStrategy {
    private FindStrategy findStrategy;

    public SearchFindAdapter(FindStrategy findStrategy) {
        this.findStrategy = findStrategy;
    }

    @Override
    public void search() {
        findStrategy.find(true); // 검색을 누르면 너는 그냥 Global 로 동영상을 검색해
    }
}
```

그리고, 이 어댑터는 우리가 만든 전략 인터페이스를 상속 받아서 우리 전략을 정의할 수 있는 형태로 만든다. 이때, 타사 `검색 전략`이 그대로 실행되도록 만들어준다.

이게 끝이다. 단지 어댑터를 만들어 주기만 하면 된다. 이제 클라이언트에서 사용해보자

우리가 만든 검색 전략을 실행하다가, `동영상` 검색이 필요해져서 사용한다고 가정한다.

```java
public class Client {
    public static void main(String[] args) {
        // 모든 컨텐츠 검색
        SearchButton searchButton = new SearchButton();
        searchButton.onclick();

        // 이미지 검색
        searchButton.setSearchStrategy(new SearchImageStrategy());
        searchButton.onclick();

        // ... (생략)

        // 타사에서 만든 동영상 검색
        searchButton.setSearchStrategy(new SearchFindAdapter(new FindVideoStrategy()));
        searchButton.onclick();
    }
}
```

```java
// 실행 결과
모든 컨텐츠 검색 전략
이미지 검색 전략
글로벌 동영상 컨텐츠 검색
```

마지막의 `동영상` 검색 전략을 실행한 모양을 보면, `어댑터`를 끼워 타사의 전략 의존성을 넣어주기만 했다. 그리고, 검색이 실행되는 함수 호출 부분인 `onclick()` 의 모양은 **그대로** 인 것을 확인 할 수 있다.

`어댑터`를 만듦으로서 기존의 우리 코드의 변경이 필요가 없었다. 그리고, 전략을 실행하는 부분인 `Client` 클래스에서도 큰 모양의 변화가 없었다.

단지, `어댑터`를 만들었고 타사의 전략에 어댑터를 끼워서 넣어주기만 했다. 그랬더니, 타사에서 만든 검색 로직임에도 실행에 문제가 없는 모습을 확인할 수 있다.

<br>

### 🧐 패턴을 적용해서 이로운 점

<br>

- 기존에 우리가 만든 전략 클래스에 변경이 일어나지 않는다
- 해당 전략을 수행하는 코드의 변경 또한 일어나지 않는다
- 만약, `동영상` 검색 모듈이 업데이트 되어 수정이 된다면 나는 `어댑터`만 수정해주면 된다
- 타사의 검색 전략이 또 추가 된다면 기존 코드의 변경 없이 또 어댑터만 만들어주면 된다

<br>

**그럼에도**, 타사 로직의 경우 모양 자체가 다르기 때문에 **별도로 신경 써주어야** 하는 ****요소가 계속 존재한다는 것은 좋지 않은 것 같다. **그리고** 이러한 유형이 추가 될 때마다 어댑터를 만들어주어야 한다는 점도 마찬가지다.

**어댑터가 많아 지게 되면** `복잡성`이 증가할 것 ****같다는 생각이 들었다. **왜냐하면**, 클라이언트에서 사용할 때, 해당 객체에 맞는 **어댑터를 찾아서 끼워주어야 하기 때문**이라고 생각한다.

<br>

---

🔗 [https://ko.wikipedia.org/wiki/팩토리_메서드_패턴](https://ko.wikipedia.org/wiki/%EC%96%B4%EB%8C%91%ED%84%B0_%ED%8C%A8%ED%84%B4)

🔗 [https://www.youtube.com/watch?v=q3_WXP9pPUQ](https://www.youtube.com/watch?v=lJES5TQTTWE)

🔗 https://velog.io/@kwj1270/Adapter-pattern
