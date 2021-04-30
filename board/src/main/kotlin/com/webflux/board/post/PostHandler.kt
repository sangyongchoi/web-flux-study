package com.webflux.board.post

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

@Component
class PostHandler(
    private var postRepository: PostRepository
) {

    fun create(serverRequest: ServerRequest): Mono<ServerResponse> {
        val post = serverRequest.bodyToMono(Post::class.java)

        return postRepository.saveAll(post)
            .flatMap { created(URI.create("/todos/${it.id}")).build() }
            .next()
    }

    fun getList(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ok().body(postRepository.findAll())
    }
}