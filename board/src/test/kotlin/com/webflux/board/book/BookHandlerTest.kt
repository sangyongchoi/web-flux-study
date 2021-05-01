package com.webflux.board.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.webflux.board.util.ServerResponseParser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@ExperimentalCoroutinesApi
@SpringBootTest
internal class BookHandlerTest {

    @Autowired
    lateinit var bookHandler: BookHandler

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun insert() = runBlockingTest {
        val mockServerRequest = MockServerRequest.builder()
            .build()

        val result = bookHandler.insert(mockServerRequest)
        val body = ServerResponseParser.parseBody(result)

        val data: List<Book> = objectMapper.readValue(body)

        assertEquals(2, data.size)
        assertEquals(data[0].title, "title1")
        assertEquals(data[1].title, "title2")
    }

    @Test
    fun select() = runBlockingTest {
        val mockServerRequest = MockServerRequest.builder()
            .build()

        val result = bookHandler.findAll(mockServerRequest)
        val body = ServerResponseParser.parseBody(result)

        val data: List<Book> = objectMapper.readValue(body)

        data.forEach(){ println(it)}
    }

    @Test
    fun flux_test() {
        val book1 = Book("title1", listOf("csy"), 2020)
        val book2 = Book("title2", listOf("ksy"), 2020)

        Flux.just(book1, book2)
            .map(Book::toString)
            .reduce(StringBuilder(), {sb,b ->
                sb.append("-")
                    .append(b)
                    .append("\n") }
            )
            .doOnNext{ println(it)}
            .subscribe()
    }

    @Test
    fun repository_test() {
        val title = Mono.delay(Duration.ofSeconds(1))
            .thenReturn("title1")
            .doOnSubscribe { println("subscribe") }
            .doOnNext { t -> println("Book title resolve $t") }

        val newYear = Mono.delay(Duration.ofSeconds(2))
            .thenReturn(2017)
            .doOnSubscribe { println("subscribed for publishing year") }
            .doOnNext { t -> println("New Publishing year resolved $t") }

        updateBookYearByTitle(title, newYear)
            .doOnNext{b-> println("Publishing year updated for book: $b")}
            .hasElement()
            .subscribe()

        Thread.sleep(5000)
    }

    private fun updateBookYearByTitle(title: Mono<String>, newPublishingYear: Mono<Int>): Mono<Book> {

        return bookRepository.findByTitle(title)
                .flatMap { book -> newPublishingYear.flatMap { year ->
                    book.changePublishingYear(year)
                    bookRepository.save(book)
                } }
    }

}