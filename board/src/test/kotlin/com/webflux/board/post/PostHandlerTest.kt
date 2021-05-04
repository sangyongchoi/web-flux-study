package com.webflux.board.post

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal class PostHandlerTest {

    private val postService = mockk<PostService>()
    private val handler = PostHandler(postService)
    private val request = mockk<ServerRequest>()

    @Test
    fun create() {
        coEvery { postService.save(any()) } returns Post("test")
        every { request.bodyToMono<Post>() } returns Mono.just(Post("test"))

        runBlocking {
            val response = handler.create(request)
            assertEquals(HttpStatus.CREATED, response.statusCode())
        }
    }

    @Test
    fun list() {
        coEvery { postService.findAll() } returns Flux.just(
            Post("test"),
            Post("test2")
        )

        runBlocking {
            val response = handler.list(request)
            assertEquals(HttpStatus.OK, response.statusCode())
        }
    }
}
