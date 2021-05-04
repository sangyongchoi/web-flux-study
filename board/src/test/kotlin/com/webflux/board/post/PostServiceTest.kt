package com.webflux.board.post

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class PostServiceTest {

    @Autowired
    lateinit var postRepository: PostRepository

    lateinit var postService: PostService

    @BeforeAll
    fun setUp() {
        postService = PostService(postRepository)
        insertDummyDate()
    }

    private fun insertDummyDate() {
        val post1 = Post("test")
        val post2 = Post("test")

        postRepository.save(post1)
        postRepository.save(post2)
    }

    @AfterAll
    fun after() {
        postRepository.deleteAll()
    }

    @Test
    @DisplayName("게시물 조회 테스트")
    @Order(1)
    fun list() {
        runBlocking {
            val result = postService.findAll()
            val count = result.count()

            count.map {
                assertEquals(2, it)
            }
        }
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    @Order(2)
    fun save() {
        // given
        val post = Post("test")

        runBlocking {
            // when
            val save = postService.save(post)

            // then
            assertNotNull(save)
        }
    }
}