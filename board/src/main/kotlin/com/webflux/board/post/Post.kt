package com.webflux.board.post

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
class Post(
    @field:Id var id:Long? = null,
    val content: String
) {

    override fun toString(): String {
        return "{ id: $id content: $content }"
    }
}