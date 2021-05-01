package com.webflux.board.book

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class BookRouter(
    private val bookHandler: BookHandler
){

    @Bean
    fun router(): RouterFunction<ServerResponse> {
        return coRouter {
            POST("/book", accept(MediaType.APPLICATION_JSON), bookHandler::insert)
        }
    }
}