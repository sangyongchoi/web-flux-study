package com.webflux.board.post.reply

import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.stereotype.Service

@Service
class ReplyService(
    private val replyRepository: ReplyRepository
) {

    suspend fun write(reply: Reply): Reply {
        return replyRepository.save(reply)
            .awaitFirstOrElse { throw RuntimeException("실행중 에러가 발생하였습니다.") }
    }
}