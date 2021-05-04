package com.webflux.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = [BoardApplication::class])
class BoardApplication

fun main(args: Array<String>) {
    runApplication<BoardApplication>(*args)
}
