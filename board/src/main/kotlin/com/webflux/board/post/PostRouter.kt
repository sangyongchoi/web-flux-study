package com.webflux.board.post

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class PostRouter(
    private var handler: PostHandler
) {

    @Bean
    fun create(): RouterFunction<ServerResponse> {
        return RouterFunctions.route(
            RequestPredicates.POST("/posts").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::create)
    }

    @Bean
    fun getList(): RouterFunction<ServerResponse> {
        return RouterFunctions.route(
            RequestPredicates.GET("/posts").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getList)
    }
}