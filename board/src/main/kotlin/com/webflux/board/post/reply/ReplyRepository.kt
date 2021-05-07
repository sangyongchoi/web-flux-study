package com.webflux.board.post.reply

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ReplyRepository: ReactiveMongoRepository<Reply, ObjectId?> {
}