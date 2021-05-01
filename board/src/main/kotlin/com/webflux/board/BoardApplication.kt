package com.webflux.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories(basePackageClasses = [BoardApplication::class])
@EnableReactiveMongoRepositories(basePackageClasses = [BoardApplication::class])
class BoardApplication

fun main(args: Array<String>) {
    runApplication<BoardApplication>(*args)
}
