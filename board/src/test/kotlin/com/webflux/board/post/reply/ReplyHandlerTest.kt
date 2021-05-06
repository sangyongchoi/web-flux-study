package com.webflux.board.post.reply

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono

internal class ReplyHandlerTest {

    val service = mockk<ReplyService>()
    val handler = ReplyHandler(service)
    val request = mockk<ServerRequest>()

    @Test
    fun write() {
        every { request.bodyToMono(Reply::class.java) } returns Mono.just(Reply(ObjectId("608ed6ab3f79f85e4f45c091"), "csytest", "test"))
        coEvery { service.write(any()) } returns Reply(ObjectId("608ed6ab3f79f85e4f45c091"), "csytest", "test")

        runBlocking {
            val response = handler.write(request)

            assertEquals(HttpStatus.OK, response.statusCode())
        }
    }
}