package com.webflux.board.post

import com.webflux.board.util.MockitoHelper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest
internal class PostRouterTest {
    @MockBean
    lateinit var postRepository: PostRepository
    private lateinit var webClient: WebTestClient

    @BeforeEach
    fun setUp() {
        val routerFunction = PostRouter(PostHandler(postRepository))
        webClient = WebTestClient
            .bindToRouterFunction(routerFunction.create())
            .build()

        Mockito.`when`(postRepository.save(MockitoHelper.anyObject()))
            .thenReturn(Mono.just(Post(1L, "test")))

        Mockito.`when`(postRepository.findAll())
            .thenReturn(
                Flux.just(
                Post(1L, "test1"),
                Post(2L, "test2")
            ))
    }

    @Test
    fun create(){
        val post = webClient
            .post()
            .uri("/posts")
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromPublisher(Mono.just(Post(content = "test")), Post::class.java))
            .exchange()
            .expectStatus().isOk
            .expectBody(Post::class.java)
            .returnResult().responseBody

        assertEquals(1L, post?.id)
        assertEquals("test", post?.content)
    }

    @Test
    fun list(){
        val post = webClient
            .get()
            .uri("/posts")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Post::class.java)
            .hasSize(2)
            .returnResult().responseBody

        post?.forEach { println(it) }
    }
}
