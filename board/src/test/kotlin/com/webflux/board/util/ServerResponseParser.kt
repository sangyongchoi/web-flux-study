package com.webflux.board.util

import org.springframework.http.codec.HttpMessageWriter
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.web.server.MockServerWebExchange
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import java.util.*

object ServerResponseParser {

    fun parseBody(serverResponse: ServerResponse): String {
        val DEFAULT_CONTEXT: ServerResponse.Context = object : ServerResponse.Context {
            override fun messageWriters(): List<HttpMessageWriter<*>> {
                return HandlerStrategies.withDefaults().messageWriters()
            }

            override fun viewResolvers(): List<ViewResolver> {
                return Collections.emptyList()
            }
        }

        // Only way I could figure out how to get the ServerResponse body was to have it write to an exchange
        val request = MockServerHttpRequest.get("http://thisdoenstmatter.com").build()
        val exchange = MockServerWebExchange.from(request)
        serverResponse.writeTo(exchange, DEFAULT_CONTEXT).block()
        val response = exchange.response
        return response.bodyAsString.block()!!
    }
}