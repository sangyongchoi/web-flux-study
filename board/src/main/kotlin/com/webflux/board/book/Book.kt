package com.webflux.board.book

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "book")
class Book(
    val title: String,
    val authors: List<String>? = null,
    var publishingYear: Int
) {

    @Id
    var id: ObjectId? = null

    fun changePublishingYear(year: Int) {
        publishingYear = year
    }

    override fun toString(): String {
        return "{ title: $title  authors: $authors publishingYear: $publishingYear }"
    }
}