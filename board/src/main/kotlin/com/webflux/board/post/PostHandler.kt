package com.webflux.board.post

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.body
import java.net.URI

@Component
class PostHandler(
    private var postRepository: PostRepository
) {

    suspend fun create(serverRequest: ServerRequest): ServerResponse {
        val post = serverRequest.awaitBody<Post>()

        return postRepository.save(post)
            .flatMap { created(URI.create("/todos/${it.id}")).build() }
            .awaitFirst()
    }

    suspend fun getList(serverRequest: ServerRequest): ServerResponse {
        return ok().body(postRepository.findAll())
            .awaitLast()
    }
}