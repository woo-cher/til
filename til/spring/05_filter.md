# Filter

> 🗒️ **필터 (Filter)**
> 
>J2EE 표준 기술로 클라이언트 요청이 DispatcherServlet 에 전달 되기 전, 후 시점으로 동작하여 요청, 응답에 대한 제어를 할 수 있는 것


💡 **J2EE ?**

```java
자바(JAVA) 기술로 기업환경의 어플리케이션을 만드는데 필요한 스펙들을 모아둔 스펙 집합임
```

# ✅ Filter 구조

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7ab3db6b-1856-4291-b393-1a71006358de/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220308%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220308T125046Z&X-Amz-Expires=86400&X-Amz-Signature=f6ba4ed79a584f982f2431fcde0e2cc31d81cf7cd84fde3382bcab4b73f088e1&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

그림과 같이 `Filter` 는 `Web Context` 계층에 위치한다. 앞서 말한 것처럼 Spring 에서 `Front Controller` 역할을 수행하는 `DispatcherServlet` 에 요청이 전달되기 전과 후 시점에 동작한다.

이 말은 즉, `Spring Context` 를 벗어나서 `Tomcat, Jetty` 등 `WAS` 계층의 전역적으로 특정 액션을 취해야 할 때 사용한다.

예를 들면, 인증, 권한 체크 이슈나 이미지 혹은 데이터에 대한 문자열 인코딩 처리 방식을 처리하는 등에 사용한다. Spring 에서도 Filter 계층을 이용해서 개발자로 하여금 보안 모듈을 제공하는데 이 모듈이 바로 `Spring Security` 이다.

또한 `Filter` 가 여러 개를 구성해서 `FilterChain` 형태를 취할 수 있다는 것이다. Filter 처리 순서에 대해서 개발자가 지정해줄 수도 있다.

# ✅ Filter 의 생명 주기

<br>

```java
package javax.servlet;

import java.io.IOException;

public interface Filter {
    default void init(FilterConfig filterConfig) throws ServletException {
    }

    void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;

    default void destroy() {
    }
}

```

`Filter` 인터페이스를 타고 들어가보면 위와 같이 생겼다. 생명 주기는 아래와 같다.

<br>

### 1️⃣ init()

- 요청이 발생하면 웹 컨테이너가 필요한 필터 객체를 초기화하고 서비스에 추가한다.

### 2️⃣ doFilter()

- Filter를 커스텀해서 사용할 때 해당 메소드를 반드시 정의해주어야 한다. 해당 메소드는 DispatcherServlet 으로 요청이 전달되기 전, 후로 필터를 동작시키는 메소드이다.
- 매개변수로 들어가는 FilterChain 의 doFilter() 메소드를 호출하면, 요청을 뒷단으로 전달한다.
- 그래서, filterChain.doFilter() 를 호출 하기 전에 어떠한 처리 과정을 넣어 줄 수 있다.

### 3️⃣ destory()

- 웹 컨테이너가 생성한 필터 객체를 제거하고 자원을 반환한다.

# ✅ Spring 에서 Filter 사용하기

<br>

생명주기에서 언급한 것처럼, 커스텀 필터를 구현하기 위해서는 위 `Filter` 인터페이스를 구현하면 된다. 요청이 핸들러까지 전달되기 전에 로그를 남기고 싶다고 가정한다.

먼저, 로그를 남기는 필터 클래스를 구현한다. 또한, Chain 형태로 구성해서 여러 필터를 만들 것이다.

만들 여러 필터들에 대해 순서를 보장하기 위해 `@Order` 주석을 사용한다.

```java
@Component
@Slf4j
@Order(1)
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In Request Logging Filter !!");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
```

```java
@Component
@Slf4j
@Order(2)
public class ResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In Response Logging Filter !!");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
```

필터를 만들어서 `bean` 으로 등록을 해두었다. 이렇게 설정하고 Spring 을 돌리면, `모든 요청`에 대해서 위 필터들이 적용될 것이다. 이제 핸들러(Controller) 하나를 만들어 `/` 경로로 요청을 보내보자.

```java
@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String hello() {
        log.info("hello() handler processing");
        return "Hello spring-til";
    }
```

```
요청 경로 : "/"

(결과)
c.study.til.filter.RequestLoggingFilter  : In Request Logging Filter !!
c.s.til.filter.ResponseLoggingFilter     : In Response Logging Filter !!
c.s.t.s.controller.HomeController        : hello() handler processing
```

정상적으로 `DispatcherServlet` 이 핸들러를 찾아서 핸들링 하기 전에 필터들이 순서에 맞게 실행된 것을 알 수 있다.

# ✅ 특정 Url 에 반응하는 Filter 만들기

<br>

모든 요청이 아닌 **특별한 경로에 대해서**만 Filter 를 만들고 싶을 수 있다. 이런 경우, `@Component` 주석을 사용해서 전역 빈으로 등록하는 것이 아니라, 별도의 `Bean` 으로 등록해서 Spring 에게 알려주어야 한다.

먼저, 특별한 경로에 대해 사용할 필터를 만든다.

```java
@Slf4j
public class SpecificUrlPatternFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In Specific Url Pattern Filter !!");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
```

그리고, 특별한 경로에 작용할 핸들러를 만든다.

```java
@RestController
@Slf4j
public class HomeController {

    ...

    @GetMapping("/specific-url-pattern")
    public String specificUrlPattern() {
				log.info("specificUrlPattern() handler processing");
        return "Test sepecific url pattern filter !!";
    }
}
```

그런 뒤, `Bean`을 등록하기 위한 설정을 진행한다. 
bean 으로 등록할 주체는`FilterRegistrationBean` 이고 해당 객체에 내가 만든 필터와 어느 url 패턴에 반응하게 할 지를 세팅해야 한다.
이는 [Spring doc](https://www.baeldung.com/spring-exclude-filter#2-filterregistration) 에서 참고하였다.

```java
@SpringBootApplication
public class TilApplication {
    public static void main(String[] args) {
        SpringApplication.run(TilApplication.class, args);
    }

    @Bean
    FilterRegistrationBean<SpecificUrlPatternFilter> specificUrlPatternFilterFilterRegistrationBean() {
        final FilterRegistrationBean<SpecificUrlPatternFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new SpecificUrlPatternFilter());
        filterRegistrationBean.addUrlPatterns("/specific-url-pattern/*");
        return filterRegistrationBean;
    }
}
```

그런 뒤 해당 url 패턴인 `/specific-url-pattern` 으로 요청을 보내보았다.

```java
c.study.til.filter.RequestLoggingFilter  : In Request Logging Filter !!
c.s.til.filter.ResponseLoggingFilter     : In Response Logging Filter !!
c.s.til.filter.SpecificUrlPatternFilter  : In Specific Url Pattern Filter !!
c.s.t.s.controller.HomeController        : specificUrlPattern() handler processing
```

원래 만들어둔 경로인 `/` 로 보내면, `SpecificUrlPatternFilter` 가 적용되지 않고 **세팅해둔 url 패턴에 맞는 요청으로 보내면 해당 필터가 작동**하는 것을 알 수 있다.

물론 이전에 만들어둔 **2개의 필터**는 `@Component` 를 사용해서 전역 bean 으로 등록해두었기 때문에 모든 요청에 대해 반응하는 것이다.

<br>

---

🔗 [https://www.baeldung.com/spring-exclude-filter](https://www.baeldung.com/spring-exclude-filter)

🔗 [https://www.youtube.com/watch?v=h66g0mFpvVE](https://www.youtube.com/watch?v=h66g0mFpvVE)

🔗 [https://mangkyu.tistory.com/173](https://mangkyu.tistory.com/173)

🔗 [https://gardeny.tistory.com/35](https://gardeny.tistory.com/35)
