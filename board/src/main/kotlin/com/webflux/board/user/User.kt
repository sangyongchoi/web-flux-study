package com.webflux.board.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
class User(
    @Id val userId: String,
    val name: String
) {

    override fun toString(): String {
        return "User(userId='$userId', name='$name')"
    }
}