package com.satotats.exercise.kotlin.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object TokenHolder {
    private val api = SomeApi()
    private lateinit var someToken: String

    private var cnt: Int = 0

    fun getToken() = runBlocking {
        println(cnt)
        if (!::someToken.isInitialized || cnt++ % 3 == 0) {
            someToken = api.heavyCall()
        }
        return@runBlocking someToken
    }
}

class SomeApi() {
    private var version: Int = 1
    suspend fun heavyCall(): String {
        print("toooooooooooooooooooo heavy, baby: ")
        delay(100)
        return "token v" + version++
    }
}

fun main() {
    repeat(100) {
        Thread {
            println(TokenHolder.getToken())
        }.start()
    }
}