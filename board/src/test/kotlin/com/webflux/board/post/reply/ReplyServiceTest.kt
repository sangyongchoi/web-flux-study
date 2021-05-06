package com.webflux.board.post.reply

import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class ReplyServiceTest {

    @Autowired
    lateinit var replyRepository: ReplyRepository

    private lateinit var replyService: ReplyService

    @BeforeAll
    fun setUp() {
        replyService = ReplyService(replyRepository)
    }

    @AfterAll
    fun after() {
        replyRepository.deleteAll()
    }

    @Test
    fun writer() {
        runBlocking {
            val reply = Reply(ObjectId("111111"),"csytest1", "test")
            replyService.write(reply)

            // success
        }
    }
}