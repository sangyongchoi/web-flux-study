package com.webflux.board.post

import org.springframework.data.annotation.Id

class Post(
    val content: String
) {

    @Id
    var id:Long? = null

    override fun toString(): String {
        return "{ id: $id content: $content }"
    }
}