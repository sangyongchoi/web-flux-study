package com.webflux.board.post.reply

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReplyService(
    private val replyRepository: ReplyRepository
) {

    suspend fun write(reply: Reply): Reply {
        return replyRepository.save(reply)
            .awaitFirstOrElse { throw RuntimeException("실행중 에러가 발생하였습니다.") }
    }

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
}