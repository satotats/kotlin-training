package com.satotats.exercise.kotlin.coroutine

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import java.time.Clock

private val log = KotlinLogging.logger { }

object TokenHolder {
    private val clock: Clock = Clock.systemDefaultZone()
    private val api = SomeApi(clock)

    private val channel = Channel<SomeToken>()

    init {
        runBlocking {
            log.info("init start")
            channel.send(api.heavyCall())
            log.info("init finish")
        }
    }

    suspend fun getToken(): String = channel.receive()
        .let { if (clock.millis() > it.expiredOn) api.heavyCall() else it }
        .also { channel.send(it) }
        .let { token -> token.value }
}

// tokenを取得中の場合、取得の終了まで読み込みを停止させたい
class SomeApi(private val clock: Clock) {
    private var version: Int = 1
    suspend fun heavyCall(): SomeToken {
        log.info("heavyCall called")
        delay(100)
        return SomeToken("token v" + version++, clock.millis())
    }
}

data class SomeToken(
    val value: String,
    val createdAt: Long
) {
    val expiredOn = createdAt + 10
}

fun main() = runBlocking {
    repeat(250) {
        launch {
            log.info("got " + TokenHolder.getToken())
        }
    }
}