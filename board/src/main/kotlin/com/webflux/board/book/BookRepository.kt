package com.webflux.board.book

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookRepository : ReactiveMongoRepository<Book, ObjectId> {

    fun findByAuthorsOrderByPublishingYearDesc(authors: Flux<String>): Flux<Book>
    fun findByTitle(title: Mono<String>): Mono<Book>
}