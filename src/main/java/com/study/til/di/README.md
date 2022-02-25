> DI (Dependency Injection)
> 
> - 의존성을 가지는 변수에게 객체를 주입한다.

ex) **A a = new A(); // A 객체 자료형 변수 a 선언 및 객체를 주입**


> IOC (Inversion of Control)
> 
> - 제어를 역전시킨다.
> 컴파일 시점에 개발자가 직접 객체 자료형을 생성 / 주입하지 않고, 
> 런타임 시점에 개발자의 코드 설정에 따라 스프링 컨테이너가 알맞은 의존성을 주입하도록 제어 권한을 넘기는 것


이전 글의 `Spring Web MVC` 에 이어서, 이번 주제는 `Spring DI / IOC`  이다. 이 3가지 요소는 Spring 의 기본 3대 요소라고 할 만큼 기본적이면서 중요한 부분이다.

## ✅ 의존성

```java
Class Foo {
    private Bar bar;

    private void doSomething() {
            bar.print(); // **Bar.class 에 print() 가 구현되어 있다고 가정한다**
    }
}
```

`의존성을 가진다` 라는 것은 뭘까..? 또한 의존성에 대해 얘기하면 `종속성`이란 단어도 나오는 것 같다.

위 예제로 설명하면 아래와 같다.

- 클래스 `Foo` 는 필드 변수 `bar` 에 **의존성**을 가진다
- 필드 변수 `bar` 는 클래스 `Foo` 에 **종속성**을 가진다.

지금 위 코드를 참고하여 아래와 같이 객체를 선언했다고 하자

```java
Foo foo = new Foo();
foo.doSomething();

// (결과) NullPointException 이 발생할 것이다
```

결과가 `NullPointException` 인 것은 당연하게도, 클래스 Foo 는 bar 에 의존성을 가지는데 이 bar 에 대한 **의존성  주입 (DI)을 해주지 않았기 때문**이다.

만약 **클래스 내에서**, 의존성을 주입한다면 아래의 모양이 될 것이다. 그러고 실행을 하면, 의존성이 주입 되었기 때문에 `NullPointException` 이 발생하지 않고, 앞서 가정했던 Bar에 print() 함수가 실행 될 것이다.

```java
 Class Foo {
		private Bar bar = new Bar(); // 클래스 내에서 주입

		private void doSomething() {
				bar.print();
		}
}
```

## ✅ 결합도

> 하나의 오브젝트에 변경이 발생했을 때, 서로 관계를 맺고 있는 다른 오브젝트에게 변화를 요구하는 정도


쉽게 말하면, 다른 의존성을 주입해야 하거나, 특정 로직이 수정되어야 할 때 의존성을 가지는 객체가 얼마나 영향을 받는 지에 따라
`낮은/느슨한 결합도`, `높은 결합도` 를 가진다고 표현한다.

</br>

- **만약 `Bar` 가 아닌 다른 객체가 필요하다면?**

</br>
A 클래스에서 코드를 직접 수정해줘야 한다. 만약 객체가 여러 개면?

**전~~부 직접 코드를 수정 해야 한다.**

그만큼 많은 변화가 요구되기에 **강한 결합도**`를 가진다고 볼 수 있다.

---

```java
💡 **높은 결합도**

> 변경이 발생할 때, 관계를 가지는 타 모듈에도 큰 영향을 끼친다
> 한 모듈에서 에러가 발생하면, 타 모듈에 연쇄적으로 문제가 발생할 가능성이 높다
> 수정 및 변경을 위한 작업이 많아질 수 있다
> 컴파일 시점에 자료형이 결정되어 유연하지 못하다
```

- **특정 비즈니스 로직을 처리하면서 필요한 객체가 수시로 바뀌어야 한다면??**

만약 서비스 로직 실행 중에 **bar 변수 객체 값이 일정하게 바뀌어야 한다고 가정**해보자. **런타임 시점**에서 다른 의존성을 주입 할 수 있는가??

### **그렇지 않다.**

서비스는 이미 실행되었고 코드는 내 손을 떠났을 것이다.

그럼 이를 위한 방법으로 **ArgsConstructor**`와 getter & setter` 함수를 정의해 사용하는 것이다.

```java
Class Foo {
    private Bar bar;
    
    public void Foo(Bar bar) { // ArgsContructor
            this.bar = bar;
    }

    public Bar getBar() { // getter
            return this.bar;
    }

    public void setBar(Bar bar) { // setter
            this.bar = bar;
    }

    ...
}
```

그래서 아래와 같이 객체가 변해야 하는 `scope`마다 정의한 함수를 호출하여 **상황에 따른 객체에 대한 의존성을 주입할 수 있다.**

```java
/**
*   1. DB 에 저장된 모든 bar 객체를 가져온다고 가정한다
*   2. 가져온 bar 들은 고유 번호들을 가진다고 가정한다.
**/
public void fooService() {
    Foo foo = new Foo();
    List<Bar> barList = db.findAll();

    for (Bar bar: barList) { // **런타임 시점에 주입하는 의존성이 계속 바뀌고 있다**
            foo.setBar(bar);
            foo.doSomething();
    }	
}
```

이렇게 함으로써 **Foo** 는 아래와 같은 변화가 생겼다.

1. 어떤 객체를 사용할지에 대한 책임을 직접 지지 않게 되었다. **( 생성자, setter )**
2. 이에 따라, **Foo** 는 **Bar** 형의 다양한 객체를 주입 받을 수 있게 되었다.
3. `Foo` 는 외부에 의해 수동적으로 주입 받은 객체로 본연의 `doSomething()` 만 실행할 수 있다.
4. 이에 따라, 테스트 작성이 용이해진다. **( Mock 객체를 만들어 주입해주기만 하면 되므로 )**

Spring F/W는 이렇게 강한 결합을 가지는 현상을 기피하고 유연한 개발을 위해서 **DI** 를 제공한다.

강한 결합을 느슨하게 하기 위해서 **생성자**와 **setter** 를 활용하여 `Foo` 의 책임을 덜었지만 개발자 입장에서는 편하지 않다. 왜냐하면, 정의해 놓은 `setter` 와 `constructor` 를 특정 시점마다 계속 써주어야 하기 때문이다.

즉, **객체에 대한 제어의 책임을 계속 생각해서 코딩을 해야 한다.**

이런 흐름이면 아래와 같은 현상들이 생긴다.

- 코드가 길어진다
- 가독성이 떨어진다
- 개발자가 제어 흐름을 신경 써야 해서 피곤함

여기서 **IOC**` 의 필요성을 알 수 있다. 개발자가 객체 간의 관계만 명시해주면, Spring 이 컴파일 시점에서 두 객체의 의존성을 결정하지 않고 런타임 시점에서 유연하게 객체 간의 의존성을 연결 시켜 준다.

즉, 객체 제어의 책임을 Spring 이 진다는 말이다. 말로만 들으면 이해가 잘 가지 않는데, 이제는 Spring DI 와 IOC 예제를 보면서 이해해보장

## ✅ Spring DI 와 IOC

Spring DI 는 아래와 같은 방법으로 의존성을 주입할 수 있다. 여기에는, 우리가 유연한 의존성 주입을 위해 사용한 생성자, setter 방식이 포함되어 있다.

### **1️⃣ 생성자 주입 (Contructor Injection)**

**생성자 주입**은 의존성이 필요한 필드 변수에 대한 생성자를 만들고 그곳에 의존성을 주입하는 방법이다.

```java
@Service
public class ConstructorInjection {

    private SomeRepository repository;

    /**
     *  생성자 DI 가 하나라면, @Autowired 생략이 가능하다
     */
    @Autowired
    public ConstructorInjection(SomeRepository repository) { **// Constructor Injection**
        this.repository = repository;
    }

    public void save(String foo) {
        repository.insert(foo);
    }
}
```

### **2️⃣ 수정자 주입 (Setter Injection)**

**수정자 주입**은 Setter 메소드를 사용하여 의존성을 주입한다. 보통, `생성자 주입`과는 달리 **의존 관계가 변경될 수 있는 경우**에 사용한다.

```java
@Service
public class SetterInjection {
    
    private SomeRepository someRepository;

    @Autowired
    public void setSomeRepository(SomeRepository someRepository) { **// Setter Injection**
        this.someRepository = someRepository;
    }

    public void save(String foo) {
        someRepository.insert(foo);
    }
}
```

### **3️⃣ 필드 주입 (Field Injection)**

필드 주입은 Class 내에 존재하는 Field 변수에 바로 의존 관계를 주입한다. 그런데, 필드 주입을 사용하면 `Field injection is not recommended` IDE 에서 경고 문구를 띄울 것이다. 이 부분은 다른 글에서 정리하도록 하겠다.

```java
@Service
public class FieldInjection {
    
    @Autowired
    private SomeRepository repository; // Field Injection
    
    public void save(String foo) {
        repository.insert(foo);
    }
}
```

위 코드를 보면 객체에 대해 **어떠한 의존성도 직접 주입하지 않았다**. 생성자 또는 수정자를 정의하긴 했지만, 내가 직접적으로 `set()` 또는 `new Foo()` 형태로 주입을 하지 않았다는 말이다.

단지, Class 가 수행해야 하는 역할과 해당 객체가 어떤 객체와 관계가 있는 지만 명시해주었다.

간단하게 위 코드를 사용하는 상황으로 가정하고 **테스트 코드를 작성해보았다.**

단순 동작 확인을 위해 Spring 컨테이너 기반으로 테스트를 진행했다.

> 물론, 실제로 테스트 코드를 작성할 때는 Spring 을 띄우지 않고 작성하는 듯 하다.

왜냐하면, 테스트 시작 전에 Spring 을 띄우는데 시간이 소요되기 때문이다. 이렇게 되면, 테스트를 하는데 발생하는 비용이 커져서 비효율적이라고 한다.

그래서, 테스트를 할 때에는 DI 를 위해 정의해둔 `생성자`를 `new`연산자로 의존성을 주입하고 사용하는 케이스가 많은 것 같다. 이 부분에 대해서는 더 찾아보고 또 정리 해두어야겠다.



```java
@SpringBootTest
@Slf4j
public class FooBarTests {

    @Autowired
    private ConstructorInjection constructorInjection;

    @Test
    void saveFoo() {
        constructorInjection.save("foo!");
    }
}
```

**(실행 결과)**

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/2d8ab523-cba1-4ac2-a7c7-50e68ba69059/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220225%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220225T064614Z&X-Amz-Expires=86400&X-Amz-Signature=1f9f991af88fcb04c0af8ac66cd97cfc7a865803607decbcc7d52b6e917ce12b&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)



개발자는 위에서 객체 관계만 명시하였고 `foo` 를 저장하기 위한 로직을 작성했다. 위 코드에서는 만들어 놓은 로직을 호출했을 뿐이다. 객체에 대해 의존성을 직접 주입하는 코드가 없다.

로직이 호출되어 실행되는 **(런타임)** 시점에, Spring 이 명시 해 놓은 관계를 토대로 객체를 직접 주입한 것이다. 기존에 개발자가 객체의 대한 제어를 신경 쓰면서 코딩할 때와 다르게, 로직에 대해서만 생각하고 해당 부분을 호출만 했다. 이게 바로, **Spring IOC** 이다. 제어의 주체가 개발자 → Spring 으로 바뀌었다는 것이다.

---

```java
💡 Spring DI 와 IOC 정리

> 객체 의존성을 Spring 이 맡아서 런타임 시점에 주입한다
> 개발자가 제어하던 부분을 Spring 이 맡아서 역할을 수행한다 (IOC)
> 이에 따라, 코드가 간결해지고 가독성이 좋아졌다
> 개발자는 비지니스 로직에 집중할 수 있어 훨씬 편리하다
> Spring 이 DI 를 제공하면서, 유연하고 확장성있게 개발할 수 있다 (느슨한 결합)
```

---

🔗 [https://devlog-wjdrbs96.tistory.com/165](https://devlog-wjdrbs96.tistory.com/165)

🔗 [https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/beans.html#beans-factory-collaborators](https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/beans.html#beans-factory-collaborators)
