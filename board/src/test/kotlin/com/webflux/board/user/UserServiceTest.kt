package com.webflux.board.user

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class UserServiceTest {

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var userService: UserService

    @BeforeAll
    fun setUp() {
        userService = UserService(userRepository)
        insertDummyDate()
    }

    private fun insertDummyDate() {
        val user = User("duplication", "csy")
        userRepository.save(user).block()
    }

    @AfterAll
    fun after() {
        userRepository.deleteAll()
    }

    @Test
    fun sign_up() {
        runBlocking {
            val user = User("csytest1", "csy")
            val save = userService.signUp(user)

            assertNotNull(save)
        }
    }

    @Test
    fun sign_up_fail_already_exists_user() {
        runBlocking {
             assertThrows<AlreadyExistsException>{
                val user = User("duplication", "csy1")
                val save = userService.signUp(user)

                 println("TEST: $save")
            }
        }
    }
}
