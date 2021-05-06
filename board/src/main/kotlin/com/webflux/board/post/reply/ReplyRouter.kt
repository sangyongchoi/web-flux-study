package com.webflux.board.post.reply

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ReplyRouter(
    private val replyHandler: ReplyHandler
) {

    @Bean
    fun replyRouterCreate(): RouterFunction<ServerResponse> {
        return coRouter {
            POST("/reply/write", accept(MediaType.APPLICATION_JSON), replyHandler::write)
        }
    }
}