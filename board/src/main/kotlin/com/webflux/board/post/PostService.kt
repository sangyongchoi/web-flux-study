package com.webflux.board.post

import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PostService(
    private val postRepository: PostRepository
) {

    suspend fun save(post: Post): Post = postRepository.save(post)
        .awaitFirstOrElse { throw RuntimeException("저장중 에러가 발생하였습니다.") }

    suspend fun findAll(): Flux<Post> = postRepository.findAll()
}