package com.webflux.board.post

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Post(
    val content: String
) {

    @Id
    var id:ObjectId? = null

    override fun toString(): String {
        return "{ id: $id content: $content }"
    }
}