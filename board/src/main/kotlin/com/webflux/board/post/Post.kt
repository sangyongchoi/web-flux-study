package com.webflux.board.post

import org.springframework.data.annotation.Id

class Post(
    @Id var id:Long? = null,
    val content: String
) {

    override fun toString(): String {
        return "{ id: $id content: $content }"
    }
}