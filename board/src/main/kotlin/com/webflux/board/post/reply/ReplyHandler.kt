package com.webflux.board.post.reply

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json

@Component
class ReplyHandler(
    private val replyService: ReplyService
) {

    suspend fun write(serverRequest: ServerRequest): ServerResponse {
        val reply = serverRequest.bodyToMono(Reply::class.java)
            .awaitFirst()

        return ok()
            .json()
            .bodyValueAndAwait(replyService.write(reply))
    }

    suspend fun delete(serverRequest: ServerRequest): ServerResponse {
        val deleteRequest = serverRequest.bodyToMono(ReplyDeleteRequest::class.java)
            .awaitFirst()

        return ok()
            .json()
            .bodyValueAndAwait(replyService.delete(deleteRequest))
    }
}