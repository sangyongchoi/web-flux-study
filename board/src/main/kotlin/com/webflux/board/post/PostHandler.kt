package com.webflux.board.post

import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*

@Service
class PostHandler(
    private val postService: PostService
) {

    suspend fun create(serverRequest: ServerRequest): ServerResponse {
        val post = serverRequest.bodyToMono<Post>().awaitSingle()
        val save = postService.save(post)

        return ServerResponse
            .status(HttpStatus.CREATED)
            .json()
            .bodyValueAndAwait(save)
    }

    suspend fun list(serverRequest: ServerRequest): ServerResponse {
        val posts = postService.findAll()

        return ServerResponse
            .ok()
            .json()
            .body(posts)
            .awaitSingle()
    }
}