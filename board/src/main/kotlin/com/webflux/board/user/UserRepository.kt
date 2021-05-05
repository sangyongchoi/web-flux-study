package com.webflux.board.user

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UserRepository: ReactiveMongoRepository<User, String>{
}