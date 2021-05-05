package com.webflux.board.user

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class UserHandler(
    private val userService: UserService
) {

    suspend fun signUp(serverRequest: ServerRequest): ServerResponse {
        val user = serverRequest.bodyToMono<User>().awaitSingle()
        val savedUser = userService.signUp(user)

        return ok()
            .json()
            .bodyValueAndAwait(savedUser)
    }
}