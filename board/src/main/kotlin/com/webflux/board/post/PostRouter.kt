package com.webflux.board.post

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

@Configuration
class PostRouter(
    private var handler: PostHandler
) {

    @Bean
    fun create(): RouterFunction<ServerResponse> {
        return coRouter {
            POST("/posts", accept(MediaType.APPLICATION_JSON), handler::create)
            GET("/posts", accept(MediaType.APPLICATION_JSON), handler::getList)
        }
    }
}