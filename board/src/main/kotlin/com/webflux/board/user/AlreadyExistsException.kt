package com.webflux.board.user

class AlreadyExistsException(
    override val message: String
): RuntimeException(message) {
}