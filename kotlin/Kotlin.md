## < 함수 >

- ### 함수와 표준 출력 `fun`, `println`

```kotlin
fun main(args: Array<String>) {
    println("Hello, world!")
}
```

- ### 반환 타입이 있는 함수
```kotlin
fun getMax(a: Int, b: Int): Int {
    return if (a > b) a else b
}
```

- ### 블록이 본문인 함수
바로 위 블럭 코드가 `블록이 본문인 함수` 임. 실제 모든 변수, 식에는 `타입`이 존재한다.

- ### 식이 본문인 함수 `타입 추론`
이런 함수 형태의 경우, `반환 타입`을 적지 않아도 컴파일러가 본문 식을 해석해서
결과 타입을 `합수 반환 타입` 으로 정해줌
```kotlin
fun getMax(a: Int, b: Int) = if (a > b) a else b
```

## < 변수 >

- ### 초기화 식이 있는 변수
```kotlin
val question = "질문" // 타입 추론 -> String
val number: Int = 3 // 원한다면 타입 명시
```
- ### 초기화 식이 없는 변수
초기화 값이 없는 변수는 지정 값이 없기 때문에 컴파일러가 추론 할 수 없으므로, 반드시 타입을 지정
```kotlin
val question: String // 반드시 변수 타입을 명시
```

- ### 변경 불가능한 참조 변수 `val`
 `(java) final` 과 같음. <br> 

** 아래와 같이, 특정 블럭에서 **단 1번의 초기화가 보장**되는 경우, `val` 을 아래와 같이 사용 가능
```kotlin
val message: String
if (true) {
    message = "..."
}
else {
    message = "..."
}
```
** `val` 은 참조 자체가 불변이지만, 그 참조가 가리키는 객체 내부 값은 변경될 수 있음
```kotlin
val arr = arrayListOf("Java") // 불변 참조 선언
arr.add("Kotlin") // 객체 내부 변경
```

- ### 변경 가능한 참조 변수 `var`

변수의 값은 변경이 가능하지만, 변수 타입은 고정돼 바뀌지 않음

** 초기화 식으로 부터 타입을 추론하고, 변수 재대입이 발생한 경우 이미 추론했던 타입을 염두하여 타입을 검사하기 때문
```kotlin
var answer = 42
answer = "no answer" // "Error: type mismatch"
```

- ### 문자열 템플릿 `$`
```kotlin
fun stringTemplate(str: String) {
    println("input is $str") // ${ .. } 로 사용하는 습관을 들이면 좋다
}

fun stringTemplate(strArr: Array<String>) {
    println("first element is ${strArr[0]}") // with { .. }
    println("first ${"element is" strArr[0]}") // with { ... "" ...}
}
```

** 문자열 내, `$` 문자를 그대로 사용하고 싶다면?
```kotlin
println("This '\$' is dollar ")
```

## < 클래스 >

- ### 간단한 클래스 비교 `with JAVA`
```java
// java
class Person {
  private final String name;
  
  public Person(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}
```

... `코틀린`으로 변환
```kotlin
// kotlin
class Person(val name: String)
```

이처럼 코드가 없이 데이터만 저장하는 클래스를 `값 객체` 라고 부름. 바뀐 모습을 보면,

1. `public` 가시성 변경자가 사라짐
2. `값 객체` 의 형태로 매우 간결해짐
3. `getter`, `setter` 를 구현해줄 필요가 없음

- ### 프로퍼티

프로퍼티란, 클래스 객체가 가지고 있는 `field`, `접근자` 임. 읽기 전용인 경우 `val`, 쓰기까지 포함시키려면 `var` 로 선언함.
즉, `setter` 를 제공할 것이냐 안할 것이냐에 따름.

- ### 프로퍼티와 클래스 예제

```kotlin
class Person {
    val name: String
    val isMarried: Boolean
}

val person = Person("Bob", true) // new 생성자 생략
println(person.name) // 직접 호출 코드지만, 자동으로 getter() 호출

person.isMarried = false // = (JAVA) person.setMarried(false)
```

- ### 커스텀 접근자
```kotlin
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() { // property getter
            return height == width
        }
    val isSquare2: Boolean
        get() = height == width
}
```

## < Enum >

- ### 간단한 enum 클래스
```kotlin
enum class Color {
    RED, ORANGE, YELLOW, GREEN
}
```

- ### 프로퍼티와 메소드가 있는 enum

```kotlin
enum class Color (
    val r: Int, val g: Int, val b: Int
) {
    RED(255, 0 , 0), ORANGE(255, 165, 0), YELLOW(255, 255, 0);
    
    fun rgb() = (r * 256 + g) * 256 + b // method in enum
}
```

- ### `when` 으로 `enum` 클래스 다루기 (Java `switch`)

** `break` 문을 작성할 필요 없이, 해당 분기만 실행 <br>
** `static import` 형태로 작성이 가능 `Color.RED -> RED`
```kotlin
fun whenExample(color: Color) {
    when (color) {
        Color.RED -> "is Red!"
        Color.ORANGE -> "is Orange!"
        Color.YELLOW -> "is Yellow!"
        Color.RED, Color.YELLOW -> "is Red OR Yellow!" // with comma
    }
}
```

- ### 'when' 에 여러 개체를 사용
```kotlin
fun whenMix(c1: Color, c2: Color) {
    when (setOf(c1, c2)) {
        setOf(RED, YELLOW) -> "is Red and YELLOW" // c1, c2 가 red, yellow
        setOf(ORANGE, YELLOW) -> "is Orange and YELLOW!" // c1, c2 가 orange, yellow
        else -> "no match color!"
    }
}
```

- ### 인자가 없는 `when`
```kotlin
fun whenWithoutParam(c1: Color, c2: Color) =
    when {
        (c1 == RED && c2 == YELLOW) || (c1 == YELLOW && c1 == RED) -> ORANGE
        // ...
        else -> "no matched color!"
    }
```

## 변수 타입 검사

- ### `타입 검사: is`
```kotlin
if (e is Int) return ...
```

- ### `스마트 캐스트`
** 한번 `is` 로 타입을 검사하고 나면, 변수를 원하는 타입으로 캐스팅 하지 않아도 됨 <br>
** 단, 해당 타입이 검사한 이후 시점에 그 값이 바뀔 수 없는 경우에만 작동 <br>
** 프로퍼티는 반드시 `val` 이어야 

- ### `명시적 캐스트: as`
```kotlin
val n = s as String
```

## 반복문

- ### `while 루프`
```kotlin
while (조건) {
    
}

do {
    /* do 선행 후, 조건이 참일 때 반복 */
} while (조건)
```

- ### `범위 루프 표현: ..`
```kotlin
val oneToTen = 1..10 // 1 .. 2 .. 3
val ontToNine = 1 until 10 // 1 .. 2 .. 9
var tenToOne = 10 downTo 1 // 10 .. 9 .. 8 .. 
var tenToOneByTwoStep = 10 downTo 1 step 2 // 10 .. 8 .. 6 ..
```