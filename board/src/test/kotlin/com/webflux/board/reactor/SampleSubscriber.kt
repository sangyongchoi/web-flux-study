package com.webflux.board.reactor

import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber

class SampleSubscriber<T>: BaseSubscriber<T>() {

    override fun hookOnSubscribe(subscription: Subscription) {
        println("subscribe")
        request(1)
    }

    override fun hookOnNext(value: T) {
        println("Value: $value")
        request(1)
    }

    override fun hookOnComplete() {
        println("BaseSubscriber Complete")
    }
}