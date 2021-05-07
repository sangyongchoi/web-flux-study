# 데이터 삭제가 안돼요

굉장히 어이없는 실수였지만 같은 이슈로 고생하시는 분이 계실까 공유합니다.

`**ReactiveCrudRepository`** 를 학습할 때 발생할 수 있는 이슈입니다.

필자는 `**webflux**` 와 `**ReactiveMongoRepository**` 를 이용하여 간단한 게시판 예제를 만들어 보고자 했습니다.

입력까지는 잘 되었으나 Delete가 되지 않는 이슈가 발생하였습니다.

코드는 아래와 같습니다.

보고 무엇이 문제인지 아시는 분은 뒤로가기를 클릭하셔도 됩니다.

```kotlin
suspend fun delete(deleteRequest: ReplyDeleteRequest) {
    val replyId = ObjectId(deleteRequest.replyId)
    val requestId = deleteRequest.requestId

    Mono.just(replyId)
        .flatMap { replyRepository.findById(replyId) }
        .map {
            if(it.writerId == requestId) {
                replyRepository.delete(it)
            } else {
                throw NotPermissionException("작성자만 삭제할 수 있습니다.")
            }
        }.awaitFirst()
}
```

삭제가 되지 않는 이유는 바로 `**subscribe**` 를 하지 않아서입니다.

`**Publisher**` 는 구독을 하지 않으면 작업을 실행하지 않는 특성을 가지고 있기때문입니다.

코드를 아래와 같이 변경하면 정상적으로 동작합니다.

```kotlin
suspend fun delete(deleteRequest: ReplyDeleteRequest) {
    val replyId = ObjectId(deleteRequest.replyId)
    val requestId = deleteRequest.requestId

    val result = Mono.just(replyId)
        .flatMap { replyRepository.findById(replyId) }
        .map {
            if(it.writerId == requestId) {
                replyRepository.delete(it)
            } else {
                throw NotPermissionException("작성자만 삭제할 수 있습니다.")
            }
        }
        .awaitFirst()
        .subscribe()
}
```