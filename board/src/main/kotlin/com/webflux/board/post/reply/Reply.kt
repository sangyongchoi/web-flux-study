package com.webflux.board.post.reply

import org.bson.types.ObjectId
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id

class Reply (
    @field:NotNull val postId: ObjectId,
    @field:NotNull val writerId: String,
    @field:NotNull val contents: String
){

    @Id
    var id: ObjectId? = null
    override fun toString(): String {
        return "Reply(postId=$postId, writerId='$writerId', contents='$contents', id=$id)"
    }


}