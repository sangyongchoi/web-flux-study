package com.webflux.board.user

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    suspend fun signUp(user: User): User {
        val userId = user.userId

        val awaitFirstOrElse = userRepository.findById(userId)
            .map {
                if(it != null) {
                    throw AlreadyExistsException("이미 존재하는 유저입니다.")
                }

                userRepository.save(user)
            }
            .awaitFirstOrElse { throw RuntimeException("실행중 에러가 발생하였습니다.") }

        return awaitFirstOrElse.awaitFirst()
    }
}