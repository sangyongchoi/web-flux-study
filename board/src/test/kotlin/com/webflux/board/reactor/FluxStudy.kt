package com.webflux.board.reactor

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.reactivestreams.Subscription
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.atomic.AtomicLong


class FluxStudy {

    @Test
    fun flux_subscribe() {
        val ints = Flux.range(1, 3)
        ints.subscribe()
    }

    @Test
    fun flux_subscribe_with_consumer() {
        val ints = Flux.range(1, 3)
        ints.subscribe{ println(it) }
    }

    @Test
    fun flux_subscribe_with_consumer_and_errorConsumer() {
        val ints = Flux.range(1, 4)
            .map { i->
                if(i<=3) i
                else throw RuntimeException("Error!! Value: $i")
            }

        ints.subscribe({i-> println(i)}, {error -> println("${error.message}")})
    }

    @Test
    fun flux_subscribe_with_consumer_and_errorConsumer_and_completeConsumer() {
        val ints = Flux.range(1, 4)

        ints.subscribe(
            { i -> println(i) },
            { error -> println("${error.message}") },
            { println("complete") }
        )
    }

    @Test
    fun flux_subscribe_with_subscription() {
        // Deprecated

        val ints = Flux.range(1, 4)
        ints.subscribe(
            { i: Int? -> println(i) },
            { error: Throwable -> System.err.println("Error $error") },
            { println("Done") },
            { sub: Subscription -> sub.request(10) }
        )
    }

    @Test
    fun flux_with_baseSubscriber() {
        val ss = SampleSubscriber<Int>()
        val ints = Flux.range(1, 4)
        ints.subscribe(
            { i: Int? -> println(i) },
            { error: Throwable -> System.err.println("Error $error") },
            { println("Done") }
        )

        ints.subscribe(ss);
    }

    @Test
    fun flux_generate() {
        val flux: Flux<String> = Flux.generate({ 0 }, {state, sink ->
            sink.next("3 x $state = ${3 * state}")
            if(state == 10) sink.complete()
            state + 1
        })

        flux.subscribe{ println(it)}
    }

    @Test
    fun flux_generate_mutable() {
        val flux: Flux<String> = Flux.generate({ AtomicLong() }, { state, sink ->
            val i = state.getAndIncrement()
            sink.next("3 x $i = ${3 * i}")

            if (i == 10L) sink.complete()
            state
        })


        flux.subscribe{ println(it)}
    }

    @Test
    fun flux_generate_with_consumer() {
        val flux: Flux<String> = Flux.generate({ AtomicLong() }, { state, sink ->
            val i = state.getAndIncrement()
            sink.next("3 x $i = ${3 * i}")

            if (i == 10L) sink.complete()
            state
        }, { state -> println("consumer state: $state") })

        flux.subscribe{ println(it) }
    }

    @Test
    fun flux_create() {
        val create = Flux.create { sink: FluxSink<Int> ->
            sink.onRequest { request ->
                for (i in 1..request) {
                    sink.next(i.toInt())
                }
            }
        }

        create.subscribe{ println(it) }
    }

    @Test
    fun flux_handle() {
        val alphabet: Flux<String> = Flux.just(-1, 30, 13, 9, 20)
            .handle { i, sink ->
                val letter = alphabet(i)
                if (letter != null) sink.next(letter)
            }

        alphabet.subscribe{ println(it) }
    }

    fun alphabet(letterNumber: Int): String? {
        if (letterNumber < 1 || letterNumber > 26) {
            return null
        }
        val letterIndexAscii = 'A'.toInt() + letterNumber - 1
        return "" + letterIndexAscii.toChar()
    }

    @Test
    fun flux_with_thread() {
        val flux = Flux.just("hello ")

        val t = Thread{ flux.map { it + "thread" }
            .subscribe{ println("$it ${Thread.currentThread().name}") }
        }

        t.start()
        //t.join()
    }

    @Test
    fun flux_publish_on() {
        val s = Schedulers.newParallel("parallel-scheduler", 4) // (1)

        val flux = Flux.range(1, 2)
            .map { i -> 10 * i }
            .publishOn(s)
            .map { i -> println("Value: $i") }

        Thread{ flux.subscribe{ println(it) } }

        s.start()
    }

    @Test
    fun flux_error_return() {
        val flux = Flux.just(10)
            .map { if( it == 10) throw RuntimeException("error") else 5 }
            .onErrorReturn(123123)

        flux.subscribe{ println(it) }
    }

    @Test
    fun suspend_test() {
        runBlocking {
            test1()
            println("안녕")
            test2()
        }
    }

    suspend fun test1(){

    }

    suspend fun test2(){
        println("2")
    }
}