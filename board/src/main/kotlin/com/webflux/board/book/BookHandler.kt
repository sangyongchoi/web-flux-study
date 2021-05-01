package com.webflux.board.book

import kotlinx.coroutines.reactive.awaitLast
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookHandler(
    private var bookRepository: BookRepository
) {

    suspend fun insert(serverRequest: ServerRequest): ServerResponse {
        val book1 = Book("title1", listOf("csy"), 2020)
        val book2 = Book("title2", listOf("ksy"), 2020)

        return ok().body(bookRepository.saveAll(Flux.just(book1, book2)))
            .awaitLast()?: throw IllegalArgumentException("에러")
    }

    suspend fun findAll(serverRequest: ServerRequest): ServerResponse {
        return ok().body(bookRepository.findByAuthorsOrderByPublishingYearDesc(Flux.just("csy")))
            .awaitLast()
    }
}