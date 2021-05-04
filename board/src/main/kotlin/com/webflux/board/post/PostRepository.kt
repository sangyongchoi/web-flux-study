package com.webflux.board.post

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PostRepository : ReactiveMongoRepository<Post, Long>{
}