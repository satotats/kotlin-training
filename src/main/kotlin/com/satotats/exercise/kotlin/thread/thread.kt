package com.satotats.exercise.kotlin.thread

import mu.KotlinLogging
import kotlin.random.Random
import kotlin.random.nextLong

private val log = KotlinLogging.logger { }
fun main() {
    repeat(10000) {
        Thread {
            log.info { "Thread $it" }
            val millis = Random.nextLong(0..10000L)
            log.info { "Thread $it is getting into sleep for $millis milli-sec..." }
            Thread.sleep(millis)
            log.info { "Thread $it got up. Good morning!" }
        }.start()
    }
}
