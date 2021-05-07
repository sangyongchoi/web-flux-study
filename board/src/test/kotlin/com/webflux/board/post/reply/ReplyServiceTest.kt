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

    var replyId: ObjectId? = null

    @BeforeAll
    fun setUp() {
        replyService = ReplyService(replyRepository)
        insertDummyData()
    }

    private fun insertDummyData() {
        runBlocking {
            val reply = Reply(ObjectId("60940c9d127121577360f681"),"csytest1", "test")
            replyId = replyRepository.save(reply)
                .map { it.id!!}
                .block()
        }
    }

    @AfterAll
    fun after() {
        runBlocking {
//            replyRepository.deleteAll()
//                .subscribe{ println("완료") }
        }
    }

    @Test
    fun writer() {
        runBlocking {
            val reply = Reply(ObjectId("60940c9d127121577360f681"),"csytest1", "test")
            replyService.write(reply)

            // success
        }
    }

    @Test
    fun delete_fail_when_not_writer() {
        assertThrows<NotPermissionException> {
            runBlocking {
                val deleteRequest = ReplyDeleteRequest("60940c9d127121577360f681", "csytest1")
                replyService.delete(deleteRequest)
            }
        }
    }

    @Test
    fun delete() {
        runBlocking {
            val deleteRequest = ReplyDeleteRequest(replyId.toString(), "csytest1")
            replyService.delete(deleteRequest)
            // success
        }
    }
}