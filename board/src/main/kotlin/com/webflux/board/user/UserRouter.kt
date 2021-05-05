package com.webflux.board.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter(
    private val userHandler: UserHandler
) {

    @Bean
    fun userRouterInfo(): RouterFunction<ServerResponse> {
        return coRouter {
            POST("/signup", accept(MediaType.APPLICATION_JSON), userHandler::signUp)
        }
    }
}