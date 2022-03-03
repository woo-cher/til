# Spring MVC

> 클라이언트의 요청을 핸들러 함으로서 광범위하고 유연하게 처리하여
> 응답을 내주기 위해 설계된 프레임워크




기본적으로 핸들러는 흔히 사용하는 `@Controller`, `@RequestMapping` 어노테이션을 기반으로 처리된다. 또한, Spring mvc 는 위와 같은 지원을 위해서 `DispatcherServlet` 에 기반하여 디자인 되었다.

## ✅ Spring MVC 처리 흐름

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ece48ed7-ae11-4db3-a4a3-7300537c6a7c/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220302%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220302T080047Z&X-Amz-Expires=86400&X-Amz-Signature=6fd3b837dc3e0c3cbe83f8a9dd4b9085da30428dd4267ed1e4cce04da6f8c7fd&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

**DispatcherServlet** 은 `Front Controller` 디자인 패턴을 적용한 것이며 **Spring mvc 에서 핵심요소**이다. 위의 Front Contoller 가 바로 DispatcherServlet 이다.

> **Front Controller Pattern**

Spring Web MVC 가 다른 많은 주요 웹 프레임워크와 공유하는 패턴
<br>

이 말이 무슨 말이냐면, 여러 컨트롤러에서 공통적으로 처리하는 부분은 Front Controller 에서 처리를 한다는 뜻이다. 공통 부분 로직을 처리하고 나서, 서로 다른 역할을 수행해야 하는 부분에 대해서는 뒷단에 Controller 에게 각각 처리할 수 있도록 설계한 디자인 패턴이다.

일반적인 Servlet 은 웹 개발을 용이하게 해주는 기능들을 제공한다. 이에 비해, `DispatcherServlet` 은 기본 클래스 `Servlet`을 상속 받아 이 이상의 기능을 수행하도록 만들었다.

또한,  `DispatcherServlet` 은 앞서 말한 Front Controller 계층에서 클라이언트의 요청을 처리하고 이에 알맞은 View 를 렌더링하기 위해서 `HandlerMapping`, `HandlerAdapter`, `ViewResolver` 등의 특수 `bean` 들을 사용한다.

## ✅ DispatcherServlet 동작 흐름

아래 사진은 위 mvc flow 를 좀 더 상세하게 나타낸 그림이다.

![spring mvc.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/af18ba65-9ad8-48d4-b8f2-ee5127b03813/spring_mvc.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220302%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220302T080134Z&X-Amz-Expires=86400&X-Amz-Signature=8ee14fc5c3149a9f6df93aa0d987746c7dc0364d7759bfdf1c12e1b0b2cdf1ee&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22spring%2520mvc.png%22&x-id=GetObject)

1️⃣ 클라이언트로부터 `요청`이 들어오고, `DispatcherSerlvet` 이 해당 요청을 받는다

2️⃣ `HandlerMapping` 요청과 알맞은 `Handler` 를 찾아 가져온다

3️⃣ 요청과 맞은 해당 `Handler` 를 지원하는 `Adpater` 를 찾아 연결한다

4️⃣ 해당 `HandlerAdapter` 빈에 정의되어 있는 handle() 메소드를 실행한다

5️⃣ 핸들러 과정에서  `view` 이름을 리턴 받는다.

6️⃣ `ViewResolver` 는 리턴 받은 `view name` 을 통해 그에 맞는 `view` 를 반환한다.

7️⃣ 최종적으로 `view` 를 가지고 `response` 객체에 담아서 클라이언트에게 전달한다.

## ✅ Code 내 실행 과정 살펴보기

기본적으로 Servlet 은 아래와 같은 생명 주기를 가진다.

```jsx
package javax.servlet;

import java.io.IOException;

public interface Servlet {
    void init(ServletConfig var1) throws ServletException;
    ServletConfig getServletConfig();
    void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;
    String getServletInfo();
    void destroy();
}
```

핵심 과정은 아래와 같다.

- **init()** 을 통해 Servlet 객체가 초기에 생성된다.
- 생성이 완료되면, **service()** 를 실행하여 servlet 이 수행하는 서비스 로직을 실행한다.
- 역할을 다한 Servlet 객체는 ServletContainer 에 의해 **destory()** 가 실행되어 제거된다.

**ServletConfig(**) 와 **getServletInfo()** 는 주 역할을 수행하기 보다, Servlet 을 생성하고 역할을 수행하기 위해서 필요한 정보를 가져오고 담는 메소드라고 생각하면 된다.

DispatcherServlet 또한 일반적으로 사용하는 Servlet 을 상속 받아 구현된 것인데, 해당 클래스로 들어 가보면 `FrameworkServlet` 을 상속 받고 있다.

```jsx
public class DispatcherServlet extends FrameworkServlet { ... }
```

일반적인 Servlet 을 통해 요청을 처리할 때, 우리는 **doGet(), doPost()** 의 메소드를 재정의해서 사용하는데, `FrameworkServlet` 클래스를 들어가보면 동일한 함수를 찾을 수 있다.

```java
public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware {
		
    ...

    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    ...

    protected final void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    ...

    try {
        this.doService(request, response); // doService 를 호출함!
    } 

    ...

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
```

`doGet` 과 `doPost` 두 함수가 `processRequest()` 함수를 호출하고 있다.  이 함수를 보면, `doService()` 를 호출 하는데, 이 부분이 아마도 일반적인 Servlet 이 service()를 수행하는 부분으로 추측된다.

하지만, doService() 는 추상 메소드로 선언만 되어있다. 이 부분은 어디 정의되어있을까? 바로 `DispatcherServlet` 에 정의되어 있다.

```jsx
protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ...

    try {
        this.doDispatch(request, response);
    }
            
    ...
}
```

여기서, `doDispatch()` 함수를 호출한다.

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ...

        try {
            try {
                ModelAndView mv = null;
                Object dispatchException = null;

                try {
                    processedRequest = this.checkMultipart(request);
                    multipartRequestParsed = processedRequest != request;
                    
                    (1) mappedHandler = this.getHandler(processedRequest);
                    ...

                    (2) HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler());
                    
                    String method = request.getMethod();
                    boolean isGet = HttpMethod.GET.matches(method);
                    if (isGet || HttpMethod.HEAD.matches(method)) {
                        long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                        if ((new ServletWebRequest(request, response)).checkNotModified(lastModified) && isGet) {
                            return;
                        }
                    }

                    if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                        return;
                    }

                    (3) mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
                    
                    if (asyncManager.isConcurrentHandlingStarted()) {
                        return;
                    }

                    this.applyDefaultViewName(processedRequest, mv);
                    mappedHandler.applyPostHandle(processedRequest, response, mv);
                } catch (Exception var20) {
                    dispatchException = var20;
                } catch (Throwable var21) {
                    dispatchException = new NestedServletException("Handler dispatch failed", var21);
                }

                (4) this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
                
            } catch (Exception var22) {
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, var22);
            } catch (Throwable var23) {
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, new NestedServletException("Handler processing failed", var23));
            }

        } finally {
            if (asyncManager.isConcurrentHandlingStarted()) {
                if (mappedHandler != null) {
                    mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
                }
            } else if (multipartRequestParsed) {
                this.cleanupMultipart(processedRequest);
            }

        }
    }

```

상단에 Dispathcher 진행 과정을 이 코드에서 아래 부분을 상세히 알 수 있었다.

**(1) HandlerMapping 과정에서 알맞은 핸들러를 가져오는 부분**

**(2) 해당 핸들러를 지원하는 Adapter 를 가져오는 부분**

**(3) 해당 핸들러에 handle() 메소드를 실행하고, 결과를 ModelAndView 객체에 담는 부분**

**(4) 여러 예외를 검사하고, Dispatch 를 계속 진행하는 부분**

그렇다면, 이후에 view 를 가져오는 과정은 어디있을까? 바로 **(4)번** 메소드인 **`processDispatchResult()`** 을 타고 들어가보면 알 수 있다.

```java
private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv, @Nullable Exception exception) throws Exception {
       	...

        if (mv != null && !mv.wasCleared()) {
            this.render(mv, request, response); <<<
            if (errorView) {
                WebUtils.clearErrorRequestAttributes(request);
            }
        } else if (this.logger.isTraceEnabled()) {
            this.logger.trace("No view rendering, null ModelAndView returned.");
        }

        ...
    }
```

여기서 `ModelAndView` 가 null 이 아니면이란 조건은 핸들러 과정을 진행하고 리턴 받은 `view name` 이 있다는 말이다. 즉, `@RestController` 가 아닌 `@Controller` 에 의해서 핸들러 과정이 진행됬다는 말인 것 같다. render() 을 타고 들어가보자.

```jsx
protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ...
    
        if (viewName != null) {
            view = this.resolveViewName(viewName, mv.getModelInternal(), locale, request); <<
            if (view == null) {
                throw new ServletException("Could not resolve view with name '" + mv.getViewName() + "' in servlet with name '" + this.getServletName() + "'");
            }
        } else {
            ...
        }

         ...

        try {
            if (mv.getStatus() != null) {
                ...
            }

            view.render(mv.getModelInternal(), request, response); << view render 수행
        } catch (Exception var8) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Error rendering view [" + view + "]", var8);
            }

            throw var8;
        }
    ...
}

...

@Nullable
protected View resolveViewName(String viewName, @Nullable Map<String, Object> model, Locale locale, HttpServletRequest request) throws Exception {
    if (this.viewResolvers != null) {
        Iterator var5 = this.viewResolvers.iterator();

        // ViewResolver가 name 과 일치하는 view 를 찾는다
        while(var5.hasNext()) {
            ViewResolver viewResolver = (ViewResolver)var5.next();
            View view = viewResolver.resolveViewName(viewName, locale);
            if (view != null) {
                return view;
            }
        }
    }

    return null;
}
```

여기서 `ViewResolver` 를 가져와, 리턴 받은 `view name` 에 맞는 `View` 를 찾는 것을 알 수 있었다.

## 🔚 정리하며..

생략된 과정들도 많고, `DispatcherServlet` 이 가지는 beanContext 도 핵심인 `HandlerMapping` , `HandlerAdapater`, `ViewResolver` 뿐만 아니라 종류가 더 많은 것으로 알고 있다.

하지만, Spring 의 핵심 3가지 요소 중 하나인 `Spring Web MVC` 프레임워크가 어떤 구조로 설계되었고, 그 구조에서 핵심 역할을 하는 `DispatcherSerlvet` 이 어떤 일을 하는지 정도는 충분히 알 수 있었다.

상세하게 모든 것을 정리하기에는 범위가 너무 많고, 다른 부분들도 공부해야 하는 분야가 많아서 생략을 피하지 못하였다 ㅠㅠ

---

🔗 [https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html)

🔗 [https://velog.io/@sorzzzzy/Spring-Boot4-5.-스프링-MVC-구조-이해](https://velog.io/@sorzzzzy/Spring-Boot4-5.-%EC%8A%A4%ED%94%84%EB%A7%81-MVC-%EA%B5%AC%EC%A1%B0-%EC%9D%B4%ED%95%B4)

🔗 [https://yeonyeon.tistory.com/103](https://yeonyeon.tistory.com/103)
