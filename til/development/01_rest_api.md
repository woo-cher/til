# Rest API

### - REST (Representational State Transfer)

> - 어떠한 기술에 대한 명칭 같은 것이 아니라 웹(Web) 과 같은 시스템을 위한 **소프트웨어 아키텍처**의 한 형식이다
>

### - API (Application Programming Interface)

> - 어플리케이션 프로그램 간 데이터 등을 상호 통신하기 위해서 그 둘의 소통을 위해 존재하는 소프트웨어이다
>

이 둘의 개념을 합쳐보면, `Rest API` 란 것은 API 를 REST 형식에 맞추어 만든 것이다.

<br>

# ✅ 인터페이스에 대하여

<br>

`인터페이스` 란 무엇일까?

- 콘솔 게임을 실행했는데 화면에 조작할 인터페이스를 고르라고 한다. 나는 게임 패드가 연결 되어있기 때문에 게임 패드로 인터페이스를 선택했다. 이제 패드를 통해서 게임을 즐길 수 있다.
- 자판기에 음료수를 뽑기 위해 금액 투입구에 돈을 넣고, 버튼을 조작해서 음료수를 뽑았다.
- 티비를 조작하기 위해 리모컨을 사용했다.

바로 이 개념이 인터페이스라 할 수 있다. 티비와 사람 간에 통신을 위해 사용하는 리모컨, 자판기와 사람 간에 상호 작용을 위해 존재하는 버튼들, 게임을 조작하기 위해 사용하는 키보드 혹은 게임패드.

→ `인터페이스`란 두 개체 사이에서 위치하여 상호 통신을 할 수 있도록 해주는 것

`REST` 는 일종의 형식이라 했다. REST API 는 프로그램 간에 정보들이 주고 받는데 있어서 흔히 사용하는 API 개발 형태라고 할 수 있다. `REST` 형식의 **핵심**은 바로 **각 요청이 어떤 목적으로 보낸 것 인지에 대한 것을 요청의 모양 자체로 추론이 가능하다는 것**에 있다.

<br>

# ✅  REST 하지 않은 예

<br>

이전에 나는 `Servlet/jsp` 를 통해서 요청에 대한 핸들러를 만든 적이 있다. 이때 또 다른 팀원과 개발했었는데 그때 웹 요청 별로 작성한 `URL` 의 형태는 아래와 같았다.

```java
/user/createUserServlet
/user/deleteUserServlet?id="userId"
/user/updateUserServlet?id="userId"

...
/board/findBoardListServlet
/board/deleteBoardServlet?boardId="2"

...
/card/addCardServlet
```

이러한 형식에 문제점은 아래와 같다.

<br>

### 🧐 문제점

<br>

- 어떤 것은 create, 또 다른 것은 add 등 명확한 패턴이 없다
- `boardId` 등의 **핵심 파라미터** 이름을 모른다면 API 로 사용하기 번거롭다
- `User` 등의 짧은 이름 객체가 아니라면, 읽는데 어려움이 있다

무엇보다, 또 다른 사람이 팀에 조인하여 개발한다고 할 때 무엇을 기준으로 endpoint 를 작성해야 하는지 의문이 들 것이다.

<br>

# ✅ REST API

<br>

바로 위에서 사용한 `Resource` 를 가져와 REST API 를 만든다고 해보겠다. REST 형식을 지키기 위한 조건과 함께 하나씩 바꿔보겠다.

<br>

### 1️⃣ URL 에는 오직 명사만 포함해야 하며, 동사를 사용하지 않는다

만약 기존에 사용자를 추가하는 행위에 대한 요청에서 동사를 제거하면 아래와 같다.

```java
/user
```

그러나, 위 형태만 가지고는 해당 요청이 무엇을 위한 요청인지 알 수 없다.

<br>

### 2️⃣ 리소스는 항상 복수형태를 취해야 한다

여기서 REST 하게 만들기 위해서 우선 리소스를 복수 형태로 작성해야 한다.

```java
/users
```

그러면 요청에 대한 `행위`는 어떻게 표현할까??

<br>

### 3️⃣ 행위는 `HTTP methods` 를 활용하여 표현한다 (동사)

`HTTP methods` 는 `resources` 를 대상으로 수행할 수 있는 몇 가지 행위를 아래와 같이 정의하고 있다.

- **GET**

`resources` 데이터들에 대헤 요청하는 행위이며, 어떠한 `side effect`도 발생 시켜서는 안된다. `side effect` 란 특정 변수의 값이 변경되는 현상을 생각하면 된다.

```java
(GET) /users : 모든 사용자들에 대한 데이터를 리턴
```

- **POST**

`database` 에 `resource`를 생성하도록 서버에 요청하는 행위이며, 대부분 web form 형식으로 body 에 담아서 전송한다.

```java
(POST) /users : 새로 가입한 사용자 데이터를 추가
```

- **PUT**

`key` 에 해당하는 `resource` 를 업데이트 하거나, 만약 존재하지 않으면 새로 생성하도록 요청하는 행위이다.

```java
(PUT) /users/tester : `tester` 라는 사용자를 업데이트 하거나 생성하도록 요청
```

- **DELETE**

`key` 에 해당하는 `resource` 를 삭제하도록 요청하는 행위이다.

```java
(DELETE) /users/tester : `tester` 라는 사용자를 삭제 요청
```

`spring` 에서는 이러한 행위를 명시하기 위해서 `@GetMapping`, `@PostMapping` 등의 주석을 제공하고 있다. 그래서 특정 메소드의 행위를 해당 주석으로 표현할 수 있다.

REST API 형식에 맞게 여러 endpoint 를 작성해보자

- `GET /users` : 모든 사용자 데이터를 조회한다
- `GET /users/tester` : tester 사용자 데이터를 조회한다
- `POST /users` : 요청 body 에 담긴 사용자를 추가한다
- `PUT /users/tester` : tester 사용자 데이터를 수정한다
- `GET /boards/3/cards` : id 값이 3번인 보드 리소스에 속한 모든 카드 데이터를 조회한다
- `DELETE /boards/3/cards/2` : id 값이 3번 보드 리소스에 포함된 카드 중에 id 값이 2번인 카드를 삭제한다

<br>

# ✅ 검색, 정렬, 필터링, 페이지네이션

<br>

이러한 작업들은 데이터 집합에 대한 쿼리에 속한다. 이는 쿼리에 조건이 추가된다는 것인데 이러한 작업들을 위한 형식은 존재하지 않는다.

이런 작업을 `REST API` 를 구성하는 방법은 바로 `GET` 메소드에 `query paramter` 형태로 파라미터를 전송하는 것이다. 몇 가지 예시를 들어보면 아래와 같다.

- `Sorting` : 정렬된 사용자 데이터를 원한다고 가정하면, `GET /users?sort=age` 형식과 같이 나이 파라미터 값을 넣음으로서 로직을 구성할 수 있을 것이다.
- `Filtering` : 필터링을 위한 조건을 전달하고 싶다면, `GET /users?age=20` 처럼, 20살 이상 또는 이하 등의 조건으로 쿼리에 사용할 수 있다.
- `Searching` : 사용자 데이터 중에서 이름에 “길동” 이 포함되는 사람을 검색한다면 `GET /users?name=길동` 과 같이 키워드를 쿼리에 사용할 수 있다.
- `Pagination` : 만약 데이터가 너무 방대하여 이를 `pagination` 처리 한다고 할 때, `page` 값이 필요하게 된다. 이런 경우 `GET /users?page=4` 과 같이 페이지 값을 넣을 수 있다. 이는 4번째 페이지에 포함된 사용자 데이터들을 조회하는 것을 뜻한다.

<br>

# ✅ Versioning

<br>

`카카오 지도 API` 에 들어가면, API 를 사용하기 위해서 `//dapi.kakao.com/v2/maps/sdk.js?appkey="key"` 와 같은 형태로 API 와 관련된 스크립트를 불러와야 한다. 이 모양에서 보면 `v2`와 같이 버전을 명시하고 있다.

만약, 내가 만든 API 가 여러 곳에서 사용하고 있고 기존 API 에서 업그레이드가 된 버전이 출시 된다면 위와 같이 `version` 을 명시함으로서 해당 API 호출에 문제가 발생 시 버전 문제로 고민하진 않을 것이다.

<br>

---

🔗 [https://www.youtube.com/watch?v=iOueE9AXDQQ&t=200s&ab_channel=얄팍한코딩사전](https://www.youtube.com/watch?v=iOueE9AXDQQ&t=200s&ab_channel=%EC%96%84%ED%8C%8D%ED%95%9C%EC%BD%94%EB%94%A9%EC%82%AC%EC%A0%84)

🔗 [https://www.youtube.com/watch?v=4DxHX95Lq2U&ab_channel=노마드코더NomadCoders](https://www.youtube.com/watch?v=4DxHX95Lq2U&ab_channel=%EB%85%B8%EB%A7%88%EB%93%9C%EC%BD%94%EB%8D%94NomadCoders)

🔗 [https://wayhome25.github.io/etc/2017/11/26/restful-api-designing-guidelines/](https://wayhome25.github.io/etc/2017/11/26/restful-api-designing-guidelines/)

🔗 [https://ko.wikipedia.org/wiki/API](https://ko.wikipedia.org/wiki/API)

🔗 [https://ko.wikipedia.org/wiki/REST](https://ko.wikipedia.org/wiki/REST)

🔗 [https://ko.wikipedia.org/wiki/SOAP](https://ko.wikipedia.org/wiki/SOAP)
