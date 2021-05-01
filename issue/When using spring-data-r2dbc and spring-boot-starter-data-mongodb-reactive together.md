# 빈 등록이 갑자기 안돼요

`spring-data-r2dbc` 의존성을 추가한 상태로 아래와 같은 코드들을 입력하여 사용하고 있었다.

```kotlin
class Post(
    @field:Id var id:Long? = null,
    val content: String
) {

    override fun toString(): String {
        return "{ id: $id content: $content }"
    }
}
```

```kotlin
interface PostRepository : ReactiveCrudRepository<Post, Long>{
}
```

```kotlin
@Service
class PostHandler(
    private val postRepository: PostRepository
) {

    suspend fun create(serverRequest: ServerRequest): ServerResponse {
        ...
    }

    suspend fun list(serverRequest: ServerRequest): ServerResponse {
        ...
    }
}
```

몽고DB를 사용하기 위하여 `pring-boot-starter-data-mongodb-reactive` 의존성을 추가했더니 잘 되던 프로젝트가 아래와 같은 오류를 뿜으면서실행이 안되기 시작했다.

```kotlin
Description:

Parameter 0 of constructor in com.webflux.board.post.PostHandler required a bean of type 'com.webflux.board.post.PostRepository' that could not be found.

Action:

Consider defining a bean of type 'com.webflux.board.post.PostRepository' in your configuration.
```

해결방법은 ReactiveCrudRepository 에 사용하는 Entity Class위에 `@Table` 어노테이션을 달아주면 된다.

```kotlin
@Table
class Post(
    @field:Id var id:Long? = null,
    val content: String
) {

    override fun toString(): String {
        return "{ id: $id content: $content }"
    }
}
```