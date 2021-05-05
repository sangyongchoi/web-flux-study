package com.webflux.board.user

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

internal class UserHandlerTest {

    val userService = mockk<UserService>()
    val userHandler = UserHandler(userService)
    val request = mockk<ServerRequest>()

    @Test
    fun sign_up() {
        coEvery { userService.signUp(any()) } returns User("handlertest", "test")
        every { request.bodyToMono<User>() } returns Mono.just(User("handlertest", "test"))

        runBlocking {
            val user = userHandler.signUp(request)

            assertEquals(HttpStatus.OK, user.statusCode())
        }
    }
}