package com.webflux.board.post.reply

import org.jetbrains.annotations.NotNull

class ReplyDeleteRequest(
    @field:NotNull val replyId: String,
    @field:NotNull val requestId: String
) {
}