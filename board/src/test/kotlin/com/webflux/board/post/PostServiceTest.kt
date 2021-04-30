package com.webflux.board.post

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PostServiceTest {

    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    fun create() {
        val post = Post("test")
        val post2 = Post("test2")
        val save = postRepository.save(post)
        val save1 = postRepository.save(post2)

        save.subscribe{ println(it)}
        save1.subscribe{ println(it)}
    }
}