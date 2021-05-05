package com.webflux.board.user

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrDefault
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    suspend fun signUp(user: User): User {
        val userId = user.userId
        val isExisting = findById(userId)

        if (isExisting != null) {
            throw AlreadyExistsException("이미 존재하는 유저입니다.")
        }

        return userRepository.save(user)
            .awaitSingle()
    }

    suspend fun findById(userId: String): User? {
        return userRepository.findById(userId)
            .awaitFirstOrDefault(null)
    }
}