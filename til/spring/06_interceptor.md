# Interceptor

> :orange_book: **인터셉터 (Interceptor)**
> 
> WAS 계층에서 Filter 과정을 거친 요청 객체를 DispatcherServlet 이 받아 적절한 핸들러로 전달하게 되는데, 이 사이에서 요청을 가로채어 어떠한 처리를 하는 것


# ✅ Interceptor 영역

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/8192d975-eaf0-467d-91bc-b7ad0a1ac966/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220310%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220310T064137Z&X-Amz-Expires=86400&X-Amz-Signature=2357fbdf8e8d52bdc55c3f59c15293b4b54a583905c47d79a54cf8af834b0152&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

위 사진에서 보면, `Interceptor` 는 `preHandler`, `postHandler`, `afterCompletion` 이 3가지 유형의 액션을 취하고 있다. 실제 JAVA 에서, `HandlerInterceptor` 인터페이스를 타고 들어가면 아래와 같이 생겼다.

```java
package org.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;

public interface HandlerInterceptor {
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
```

즉, 내가 쓰고 싶은 `interceptor` 를 만드려면 위 인터페이스를 상속 받아 메소드를 구현하면 된다.

# ✅ HandlerInterceptor 의 메소드
<br>

### 1️⃣ preHandle()

- 요청이 핸들러(컨트롤러)에 도달하기 전 시점에 액션을 취할 수 있다.
- 리턴 타입은 `boolean` 이며, `false` 가 리턴 되면 해당 요청은 다음 단계로 전달되지 않고 중지된다. 즉, 어떠한 핸들러도 해당 요청을 처리하지 않게 된다는 뜻이다.

### 2️⃣ postHandle()

- 컨트롤러에게 요청이 전달된 이후 시점에 액션을 취할 수 있다.

### 3️⃣ afterCompletion()

- 컨트롤러가 `jsp` 와 같은 `view` 를 제공한 이후 시점에 액션을 취할 수 있다.
- 이미 요청에 대한 처리를 하고 난 이후에 응답으로서 `view`가 제공 된 이후 시점이다.

# ✅ Interceptor 사용해보기 (spring-boot)
<br>

우선, 사용자 목록과 상품 목록을 보여주는 핸들러 2개를 아래와 같이 만들었다.

```java
@RestController
@Slf4j
public class UserController {

    @GetMapping("/users")
    public List<String> getUsers() {
        final List<String> users = new ArrayList<>();
        users.add("Foo");
        users.add("Bar");
        users.add("Baz");

        log.info("In Handler (UserController) method");
        return users;
    }
}

@RestController
@Slf4j
public class ProductController {

    @GetMapping("/products")
    public List<String> getUsers() {
        final List<String> products = new ArrayList<>();
        products.add("hammer");
        products.add("drill");
        products.add("dryer");

        log.info("In Handler (ProductController) method");
        return products;
    }
}
```

## 1️⃣ 전역 Interceptor

모든 요청에 대해서 해당 요청을 가로채고 로그를 찍는 `interceptor` 를 만들어보자.

```java
@Slf4j
public class GeneralPurposeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("In preHandle method of GeneralPurposeInterceptor");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("In postHandle method of GeneralPurposeInterceptor");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("In afterCompletion of GeneralPurposeInterceptor");
    }
}
```

앞서 언급한 `HandlerInterceptor` 를 상속 받아서 3가지 메소드를 재정의하였다. 이 인터셉터는 모든 요청에 대해서 각 메소드의 실행 시점마다 로그를 남기는 인터셉터이다.

여기서 끝나는 것이 아니라, 만든 인터셉터를 등록해주어야 하는데, spring 의 mvc 설정을 담당하는 인터페이스인 `WebMvcConfigurer` 를 아래와 같이 상속 받아야 한다.

```java
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GeneralPurposeInterceptor());
    }
}
```

그리고, 나는 Spring 에게 인터셉터를  추가할 것이라고 알려줄 것이기 때문에 `addInterceptors` 메소드만 재정의해서 만든 인터셉터를 등록하는 코드를 추가했다.

이제 서버를 키고 `/users` 경로로 요청을 보내면 아래와 같은 로그가 찍히는 것을 확인 할 수 있다.

```java
c.s.t.i.GeneralPurposeInterceptor        : In preHandle method of GeneralPurposeInterceptor
c.study.til.interceptor.UserController   : In Handler (UserController) method
c.s.t.i.GeneralPurposeInterceptor        : In postHandle method of GeneralPurposeIntercepto
c.s.t.i.GeneralPurposeInterceptor        : In afterCompletion of GeneralPurposeInterceptor
```

앞서 언급한 것과 같이 각 메소드의 실행 시점이 서로 다르다는 것을 알 수 있다.

## 2️⃣ 특정 경로에만 액션을 취하는 Interceptor

전역적으로 반응하는 것이 아니라, 어떠한 경로에 한해서 동작하는 인터셉터를 만들어보자. 앞서 만든 2가지 핸들러 중에, `상품 목록` 핸들러에 대해서만 동작하도록 해보자. 인터셉터를 만드는 방법은 위와 동일하게 `HandlerInterceptor` 를 상속 받으면 된다.

```java
@Slf4j
public class SinglePurposeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("In preHandle method of SinglePurposeInterceptor");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("In postHandle method of SinglePurposeInterceptor");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("In afterCompletion method of SinglePurposeInterceptor");
    }
}
```

그런 뒤에, 해당 인터셉터를 똑같이 spring 에게 등록할 것임을 알려주어야 한다. 이때, 추가적으로 **어떤 경로에 반응 할지** 정해줄 수 있다. 또한, `order()` 메소드를 이용해서 여러 인터셉터들에 대해 `동작 순서`를 결정해줄 수 있다.

```java
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GeneralPurposeInterceptor()).order(1);
        registry.addInterceptor(new SinglePurposeInterceptor()).addPathPatterns("/products/**").order(2);
    }
}
```

이제, `/products` 로 요청을 보내어 보면 아래와 같이 로그가 찍히는 것을 볼 수 있다.

```java
c.s.t.i.GeneralPurposeInterceptor        : In preHandle method of GeneralPurposeInterceptor
c.s.t.i.SinglePurposeInterceptor         : In preHandle method of SinglePurposeInterceptor
c.s.til.interceptor.ProductController    : In Handler (ProductController) method
c.s.t.i.SinglePurposeInterceptor         : In postHandle method of SinglePurposeInterceptor
c.s.t.i.GeneralPurposeInterceptor        : In postHandle method of GeneralPurposeInterceptor
c.s.t.i.SinglePurposeInterceptor         : In afterCompletion method of SinglePurposeInterce
c.s.t.i.GeneralPurposeInterceptor        : In afterCompletion of GeneralPurposeInterceptor
```

여기서 한 가지 **의문점**이 들었다. 자세히 보면, `preHandle` 단계에서만 `order` 가 다르게 찍히는 것을 알 수 있는데 분명히 나는 `Single` 인터셉터의 우선 순위를 뒤로 두었는데 말이다.

그래서 디버깅을 돌려보던 중에  `HandlerExcecutionChain` 클래스에서 아래 코드를 발견했다.

```java
boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for(int i = 0; i < this.interceptorList.size(); this.interceptorIndex = i++) {
            HandlerInterceptor interceptor = (HandlerInterceptor)this.interceptorList.get(i);
            if (!interceptor.preHandle(request, response, this.handler)) {
                this.triggerAfterCompletion(request, response, (Exception)null);
                return false;
            }
        }

        return true;
    }

void applyPostHandle(HttpServletRequest request, HttpServletResponse response, @Nullable ModelAndView mv) throws Exception {
    for(int i = this.interceptorList.size() - 1; i >= 0; --i) {
        HandlerInterceptor interceptor = (HandlerInterceptor)this.interceptorList.get(i);
        interceptor.postHandle(request, response, this.handler, mv);
    }

}

void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, @Nullable Exception ex) {
    for(int i = this.interceptorIndex; i >= 0; --i) {
        HandlerInterceptor interceptor = (HandlerInterceptor)this.interceptorList.get(i);

        try {
            interceptor.afterCompletion(request, response, this.handler, ex);
        } catch (Throwable var7) {
            logger.error("HandlerInterceptor.afterCompletion threw exception", var7);
        }
    }

}
```

`preHandle` 를 등록하는 메소드는 인터셉터가 담긴 리스트를 **0번 인덱스부터 파싱**하는데, `postHandle` 과 `afterCompletion` 의 경우 **배열의 맨 뒷단에서부터 가져온다는 것**이다.

조금 생각을 해보니 다음과 같은 결론이 나왔다. **메소드 안에 메소드를 호출 하는 것을 생각**해보면 된다.

```java
public void sub() {
	 sub2();
}
```

위 코드에서 sub() 를 실행하면, sub2() 의 로직이 먼저 끝나야지만 sub() 가 끝이난다. 즉, 제일 먼저 실행 된 녀석은 마지막에 종료된다는 소리이다.

다시 돌아와서, 내가 만든 `전역 인터셉터를 A`, `싱글 인터셉터를 B`라고 해보자. 그러면, 인터셉터 우선 **순위를 A → B** 로 두었기 때문에 **A.preHandle() →B.preHandle() → ... → B.postHandle() → A.postHandle() → B.afterCompletion() → A.afterCompletion()** 순서로 끝나야 한다. 따라서, 위 결과는 정상적으로 출력 된 로그임을 알 수 있었다.

아무튼 이렇게 인터셉터를 등록해서, 요청 처리 전, 후와 응답 처리가 진행된 시점에 각각 동작하는 코드를 처리할 수 있었다.

이 부분의 경우 `Servlet Filter` 에서도 처리가 물론 가능하지만 두 기능은 명백하게 다른 영역에서 동작한다는 점과, `Interceptor` 의 경우 `Spring` 영역 안에서 동작하기 때문에 보다 요청 객체에 대해서 다룰 수 있는 범위가 더 큰 것 같다.

---

🔗 [https://www.youtube.com/watch?v=agBadIAx0Wc](https://www.youtube.com/watch?v=agBadIAx0Wc)

🔗 [https://velog.io/@dhk22/Spring-MVC-11-Spring-Interceptor를-이용한-사용자-권한-검증-및-분기-처리](https://velog.io/@dhk22/Spring-MVC-11-Spring-Interceptor%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%82%AC%EC%9A%A9%EC%9E%90-%EA%B6%8C%ED%95%9C-%EA%B2%80%EC%A6%9D-%EB%B0%8F-%EB%B6%84%EA%B8%B0-%EC%B2%98%EB%A6%AC)

🔗 [https://popo015.tistory.com/115](https://popo015.tistory.com/115)
