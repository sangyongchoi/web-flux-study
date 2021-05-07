package com.webflux.board.post.reply

class NotPermissionException(
    override val message: String
): RuntimeException(message) {
}