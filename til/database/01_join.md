# :one: JOIN
<br>

> 
> 관계형 데이터베이스에서 여러 개로 분산 되어 있는 정보들을 결합해서 하나의 테이블로 만드는 방법

<br>

# ✅ 중복된 데이터가 있는 테이블

<br>

테이블 `topic` 이 아래와 같이 있다.

<br>

**Topic**

| tid | title | description | name | city | job_title |
| --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | egoing | jeju | developer |
| 2 | CSS | CSS is … | leezche | jeju | designer |
| 3 | Database | Database is .. | egoing | jeju | developer |

<br>

이 테이블의 구조는 데이터를 한 눈에 볼 수 있다는 장점이 있지만, `name`, `city`, `job_title`, `job_desc` column 들에 데이터들이 중복이 발생한다는 문제가 있다.

**만약,** 존재하는 데이터 row 가 약 `1천 만개`를 넘는다고 가정한다. 이들 중에 **중복되는 데이터 값들이 많을 것**이고 이는 데이터가 차지하는 공간이 많이 소모된다고 볼 수 있다.

이때, 특정 `row` 가 **사는 지역이 바뀌었다고** 하면 해당 `row` 를 **직접 찾아서 데이터 값을 바꾸어 주어야** 한다.

또한, 이는 `데이터의 무결성`이 보장될 수 없다. 만약 특정 데이터의 사는 지역을 `jeju` 로 바꾸려는 과정에서 `jejy` 라고 오타가 날 수도 있을 것이다. 이렇게 되면, 다른 `jeju` 와는 의미하는 바가 달라지게 된다.

이 상태에서 테이블 `comment` 가 아래와 같이 추가되었다고 가정해보자.

<br>

**Comment**

| cid  | description | name | city | job_title | job_description |
| --- | --- | --- | --- | --- | --- |
| 1 | lorem ... | egoing | jeju | developer | developer is … |

댓글을 누가 썼고, 그 사람의 이름, 사는 지역 등의 데이터가 또 필요하여 작성했을 것이다. 그러면 여기서 또 중복이 발생하고, 한 사람의 사는 지역이 바뀌면 다른 테이블에서도 직접 바꾸어주어야 한다.

<br>

# ✅ 테이블 쪼개기

<br>

중복이 발생하는 문제를 해결하기 위해서 해당 컬럼들을 모두 모아 다른 테이블로 모두 분리한다. 따라서, 아래와 같은 형태로 테이블을 쪼갤 수 있다.

<br>

---

**Topic**

| tid | title | description | author_id |
| --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 |
| 2 | CSS | CSS is … | 2 |
| 3 | Database | Database is .. | 1 |

---

**Author**

| id | name | city | profile_id |
| --- | --- | --- | --- |
| 1 | egoing | seoul | 1 |
| 2 | leezche | jeju | 2 |

---

**Profile**

| id | job_title | job_description |
| --- | --- | --- |
| 1 | developer | developer is … |
| 2 | designer | designer is … |

---

테이블을 쪼개어서 중복되는 데이터를 해결할 수 있었다. 만약, `egoing` 이라는 사람의 사는 지역이 `changwon` 으로 바뀐다면 `Author` 테이블로 접근하여 데이터만 수정해주면 된다.

이처럼 테이블을 쪼개었을 때, `쓰기` 작업이 굉장히 간단해 지는 것을 알 수 있다. 하지만, 쪼개지 않은 경우와 비교해보았을 때 `읽기` 작업에서는 불편함이 존재한다.

<br>

# ✅ JOIN

<br>

바로 위에서 테이블을 쪼개어 `쓰기`작업이 편하도록 했다. 하지만 이는 `읽기` 가 불편하다는 단점이 존재했다. 이제, `JOIN` 문을 이용해서 여러 개로 쪼개진 테이블이 마치 쪼개기 전의 모양처럼 만들어보려고 한다. 이를 통해서, 데이터를 한 눈에 알아볼 수 있도록 해보자.

## ✔️ 실습 데이터

<br>

실습 데이터는 아래 3개의 테이블을 이용한다.

<br>

---

### **Topic**

| tid | title | description | author_id |
| --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 |
| 2 | CSS | CSS is … | 2 |
| 3 | Database | Database is .. | 1 |
| 4 | Oracle | Oracle is … | NULL |

---

### **Author**

| aid | name | city | profile_id |
| --- | --- | --- | --- |
| 1 | egoing | seoul | 1 |
| 2 | leezche | jeju | 2 |
| 3 | blackdew | namhae | 3 |

---

### **Profile**

| pid | title | description |
| --- | --- | --- |
| 1 | developer | developer is … |
| 2 | designer | designer is … |
| 3 | DBA | DBA is .. |

---

먼저 데이터를 분석해보면 아래와 같다.

- `topic` 테이블의 마지막 `row` 에 `author_id` 값이 존재하지 않는다
- `author` 테이블의 `id(3)` 데이터는 `topic` 테이블에서 사용되지 않고 있다

<br>

### 1️⃣ LEFT & RIGHT JOIN

<br>

먼저, `topic` 의 데이터가 보고 싶어서 `select * from topic` 쿼리를 수행했다고 하자. 그럼 해당 테이블의 정보들이 아래와 같이 출력 될 것이다.

| tid | title | description | author_id |
| --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 |
| 2 | CSS | CSS is … | 2 |
| 3 | Database | Database is .. | 1 |
| 4 | Oracle | Oracle is … | NULL |

이때, `author_id` 라는 컬럼 대신에 위에서 테이블을 쪼개지 않았을 때와 같이 한 눈에 알아보기 쉬운 형태로 데이터를 출력하기 위해서는 어떻게 해야 할까??

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/2aadbe42-8143-4842-80d8-ec944e8e453c/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220330%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220330T100316Z&X-Amz-Expires=86400&X-Amz-Signature=9dfea712287a15dc5efc80c2188f8e72334e72924ee3b9056f9486a3acb049ec&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)


이 경우 **(LEFT)** 위와 같은 형태의 다이어그램이 나온다. `A` 구역에는 `topic` 테이블이 해당되며, `topic` 에서 나는 `author_id` 와 관련된 데이터를 나열하려고 했으므로 `B` 구역에는 `author` 테이블이라고 할 수 있다.

그리고, `A` 테이블에서 `author_id` 값에 대한 정보를 얻어오려고 했기 때문에 `LEFT JOIN` 을 사용하면 된다. 따라서, `sql` 을 아래와 같이 작성할 수 있다.

<br>

```sql
-- `topic` 의 author_id 와 대응 되는 값을 `author` 에서 가져와
SELECT * FROM topic
LEFT JOIN author ON topic.author_id = author.aid;
```

`LEFT JOIN` 문 결과이다.

| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 |
| 3 | Database | Database is .. | 1 | 3 | blackdew | namhae | 3 |
| 4 | Oracle | Oracle is … | NULL | NULL | NULL | NULL | NULL |

<br>
모양을 보면 기존 `topic` 테이블에서, `author_id` 인 `key` 값과 대응되는 데이터들을 `author` 에서 가져온 것을 알 수 있다. 이때, 대응하는 값이 없는 `key` 에 대해서는 `NULL` 로 표현되고 있다.

이제 이 모양에서 또 `profild_id` **데이터를 이어 붙이고 싶을 수 있을 것**이다.

그럼 어떻게 해야 할까?? —> 그냥 `JOIN` **문을 하나 추가**해주면 된다.

<br>

```sql
-- `author` 의 profile_id 와 대응 되는 값을 `profile` 에서 가져와
SELECT * FROM topic
LEFT JOIN author ON topic.author_id = author.aid
LEFT JOIN profile ON author.profile_id = profile.pid;
```

| tid | title | description | author_id | aid | name | city | profile_id | pid | title | description |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 | 1 | developer | developer is … |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 | 2 | designer | designer is … |
| 3 | Database | Database is .. | 1 | 3 | blackdew | namhae | 3 | 3 | DBA | DBA is .. |
| 4 | Oracle | Oracle is … | NULL | NULL | NULL | NULL | NULL | NULL | NULL | NULL |

<br>
따라서 `LEFT JOIN` 은,

> 자신이 **가지고 있는 데이터들**과, 다른 테이블과 **링크되어 있지 않은 데이터 모두** 가져온다

<br>

여기서 **보고 싶은 컬럼만** 선택하여 추출할 수도 있다. 예를 들어, `tid`, `topic 테이블의 itle`, `author_id`, `name`, `profile 테이블의 title`만 뽑고자 한다면?

이렇게 사용할 수 있다.

```sql
-- `title` 컬럼은 topic, profile 두 테이블에 모두 존재한다
-- 따라서, 테이블을 명시해주지 않으면 DB 는 어디 테이블의 컬럼인지 모르므로 에러가 발생

SELECT tid, topic.title, author_id, name, profile.title AS job_title FROM topic
LEFT JOIN author ON topic.author_id = author.aid
LEFT JOIN profile ON author.profile_id = profile.pid;
```

| tid | title | author_id | name | job_title |
| --- | --- | --- | --- | --- |
| 1 | HTML | 1 | egoing | developer |
| 2 | CSS | 2 | leezche | designer |
| 3 | Database | 1 | egoing | DBA |
| 4 | Oracle | NULL | NULL | NULL |

<br>

조건문인 `WHERE`도 사용 가능하며 그 밖에 `ORDER BY`, `GROUP BY` 등 모두 사용할 수 있다.

`RIGHT JOIN` 의 경우, 기준이 되는 테이블이 달라 질 뿐이기 때문에 상황에 맞게 사용하면 된다.

<br>

### 2️⃣ INNER JOIN

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/2dcc267c-156d-4936-9e13-b5689f168416/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220330%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220330T100430Z&X-Amz-Expires=86400&X-Amz-Signature=ff09637efbb903d58e3bd5448d069dc2554e9188fa41257bc472ae3f91ca3eba&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`INNER JOIN` 의 다이어그램 형태이다. 연관 되어 있지 않는 데이터를 `NULL` 로 표현하는 `LEFT & RIGHT JOIN` 과는 다르게 여기에서는 `NULL` 값의 데이터가 출력 되지 않는다. 두 테이블 `A`, `B` 모두가 공통으로 가지는 데이터만 출력해주기 때문이다.

쉬운 예로, 위에 실습 전 데이터를 보면 된다. 이때 데이터의 특이점에 대해서 정리했었다. 그 중 하나가`topic` 의 마지막 행 데이터는 `author` 에 관련된 값이 존재하지 않는 것이었다.

만약, 내가 데이터를 질의할 때 `topic` 과 `author` 두 테이블 모두와 연관되어 있는 것들만 보고 싶다고 할 때 `INNER JOIN` 을 사용하면 된다.

```sql
SELECT * FROM topic INNER JOIN author ON topic.author_id = author.aid;
```

이 결과로 아까 말한 `NULL` 데이터가 제외하고 출력이 된다.

| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 |
| 3 | Database | Database is .. | 1 | 3 | blackdew | namhae | 3 |

마찬가지로 여기서 `profile_id` 와 관련된 데이터를 모두 보고자 한다면 `INNER JOIN` 문을 하나 추가하면 된다.

<br>

```sql
SELECT * FROM topic
INNER JOIN author ON topic.author_id = author.aid
INNER JOIN profile ON author.profile_id = profile.pid;
```

| tid | title | description | author_id | aid | name | city | profile_id | aid | name | city |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 | 1 | egoing | seoul |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 | 2 | leezche | jeju |
| 3 | Database | Database is .. | 1 | 3 | blackdew | namhae | 3 | 3 | blackdew | namhae |

<br>
`LEFT & RIGHT JOIN` 과는 달리 `NULL` **데이터가 보이지 않는 것을 확인**할 수 있다.

따라서 `INNER JOIN` 은

> **각각의 테이블 모두가 가지고 있는 데이터들**만 가져온다

<br>

### 3️⃣ FULL OUTER JOIN

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9384760b-10df-455a-9cb3-94a662ceec96/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220330%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220330T100503Z&X-Amz-Expires=86400&X-Amz-Signature=04f72f3fd799db9c36bbf3a4632327be1edcb634e652cc179b1f5b82a77fd841&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`FULL OUTER JOIN` 의 다이어그램 형태는 위와 같이 표현할 수 있다. 이는 두 테이블 `A`, `B` 가 공통으로 가지고 있는 데이터와, `A`만 가지고 있는 데이터 그리고 `B`만 가지고 있는 데이터도 모두 가져온다.

쉽게 말해서, (`LEFT JOIN` 의 테이블 + `RIGHT JOIN` 의 테이블) 의 결과가 `FULL OUTER JOIN` 이다. 단, **이 결과에서 중복된 데이터는 빼고 출력**된다.

먼저, `LEFT JOIN` 을 아래와 같이 질의한 결과를 보자.

<br>

```sql
SELECT * FROM topic LEFT JOIN author ON topic.author_id = author.id
```

| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 |
| 3 | Database | Database is .. | 1 | 3 | blackdew | namhae | 3 |
| 4 | Oracle | Oracle is … | NULL | NULL | NULL | NULL | NULL |


<br>
그리고, `RIGHT JOIN` 을 아래와 같이 질의하여 결과를 보면 아래와 같다.

```sql
SELECT * FROM topic RIGHT JOIN author ON topic.author_id = author.id
```

`RIGHT` 영역 테이블인 `author` 의 데이터들과 `topic` 에 매칭 되는 데이터들을 모두 가져온다. 그리고, 매칭되는 데이터가 없는 `baclkdew` 같은 행의 경우는 `NULL` 과 함께 출력된다.

| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 |
| 3 | Database | Database is .. | 1 | 1 | egoing | seoul | 1 |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 |
| NULL | NULL | NULL | NULL | 3 | blackdew | namhae | 3 |

<br>
앞에서 `FULL OUTER JOIN` 의 (`LEFT JOIN` 의 테이블 + `RIGHT JOIN` 의 테이블) 의 결과와 같다고 했다.  여기서`LEFT JOIN` 의 결과와 `RIGHT JOIN` 결과를 붙여준 후, 중복되는 데이터를 빼주면 바로 `FULL OUTER JOIN` 의 결과가 된다.

<br>

**(FULL OUTER JOIN)**

```sql
-- 안타깝게도, FULL OUTER JOIN 문법은 대다수 DB 가 지원하지 않는다.
-- 하지만, 이를 사용하기 위한 방법으로 집합 명령어를 함께 사용한다
(SELECT * FROM topic LEFT JOIN author ON topic.author_id = author.id) UNION
(SELECT * FROM topic RIGHT JOIN author ON topic.author_id = author.id)
```

| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 |
| 3 | Database | Database is .. | 1 | 1 | egoing | seoul | 1 |
| 4 | Oracle | Oracle is … | NULL | NULL | NULL | NULL | NULL |
| NULL | NULL | NULL | NULL | 3 | blackdew | namhae | 3 |

<br>

`topic` **테이블에서만 존재하는 데이터인 4번째 행**, `author` **테이블에서만 존재하는 데이터인 5번째 행** 모두 `NULL` 값과 함께 **출력** 되는 것을 알 수 있다.


<br>
따라서, `FULL OUTER JOIN` 은

> A, B 두 테이블이 공통으로 가지고 있는 데이터들을 출력하고 **A 테이블만** 가진 데이터와, **B 테이블만** 가진 데이터 모두를 출력한다

<br>

### 4️⃣ EXCLUSIVE (LEFT & RIGHT) JOIN

<br>

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/a248f9a8-6b5f-4557-acc7-54c56b1fb4df/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220330%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220330T100554Z&X-Amz-Expires=86400&X-Amz-Signature=a8e5a8abb0fbc3aa71e7af157466df54e4a1a3033665474ee222d167d1d1c205&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

`EXCLUSIVE JOIN` 은 위와 같은 형태를 띈다. 이는 `JOIN` 문의 일부가 아니라, 기존의 `LEFT & RIGHT JOIN` 등을 조합해서 만들어진 데이터 추출의 또 다른 방법이다.

따라서, 그림에서 보면 `EXCLUSIVE JOIN` 은 `A` 영역에서만 가지고 있는 데이터만 취하는 방법이다.

앞에 데이터 셋을 다시 가져와 보면, `topic` 테이블에서만 가지고 있는 데이터가 있었다. 바로 `id = 4` 값인 `Oracle` 데이터이다. 그래서 이 데이터만 추출해서 보고 싶을 때 `EXCLUSIVE JOIN`를 사용할 수 있다.

먼저, 맨 앞에서 했던 `LEFT JOIN` 결과를 가져왔다.


| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HTML | HTML is … | 1 | 1 | egoing | seoul | 1 |
| 2 | CSS | CSS is … | 2 | 2 | leezche | jeju | 2 |
| 3 | Database | Database is .. | 1 | 3 | blackdew | namhae | 3 |
| 4 | Oracle | Oracle is … | NULL | NULL | NULL | NULL | NULL |

<br>

여기서, 맨 마지막 행 데이터`(topic 에 만 존재하는)` 만 뽑고 싶다면 어떻게 해야 할까? 바로, `JOIN` 한 테이블의 `primary key` 값이 `NULL` 인 것을 가져오면 되는 것이다.

그러면, 기존 `LEFT JOIN` 에 조건문인 `WHERE` 절만 하나 추가되면 되는 것이다.

<br>

**(EXCLUSIVE LEFT JOIN)**

```sql
SELECT * FROM topic
LEFT JOIN author ON topic.author_id = author.aid
WHERE author.aid is NULL
```

| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 4 | Oracle | Oracle is … | NULL | NULL | NULL | NULL | NULL |

반대로, `RIGHT JOIN` 의 경우에서 `EXCLUSIVE JOIN` 을 사용한다면 `JOIN` 한 테이블이 `topic` 이 되므로 이 테이블의 `key` 값이 `NULL` 인 조건을 추가하면 된다.

<br>

**(EXCLUSIVE RIGHT JOIN)**

```sql
SELECT * FROM topic
RIGHT JOIN author ON topic.author_id = author.aid
WHERE topic.tid is NULL
```

| tid | title | description | author_id | aid | name | city | profile_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| NULL | NULL | NULL | NULL | 3 | blackdew | namhae | 3 |

<br>

따라서, `EXCLUSIVE JOIN` 은

> 한 테이블에만 유일하게 존재하는 데이터를 출력한다.

<br>

---

🔗 [https://www.youtube.com/watch?v=2Xa54XBXbk0&list=PLuHgQVnccGMAG1O1BRZCT3wkD_aPmPylq&index=1&ab_channel=생활코딩](https://www.youtube.com/watch?v=2Xa54XBXbk0&list=PLuHgQVnccGMAG1O1BRZCT3wkD_aPmPylq&index=1&ab_channel=%EC%83%9D%ED%99%9C%EC%BD%94%EB%94%A9)

🔗 [https://sql-joins.leopard.in.ua/](https://sql-joins.leopard.in.ua/)
