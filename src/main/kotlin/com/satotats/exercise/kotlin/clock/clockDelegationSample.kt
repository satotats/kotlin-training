package com.satotats.exercise.kotlin.clock

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.time.*

interface ClockReference {
   val clock: Clock
}

object ClockProvider : ClockReference, KoinComponent {
    override val clock: Clock by inject()
}

interface ISomething {
    fun doSomethingWithDate()
}

// 実装するinterfaceに ClockReference を追加する
class Something : ISomething, ClockReference by ClockProvider {
    override fun doSomethingWithDate() {
        val today = LocalDate.now(clock) // ClockReferenceのフィールドを参照
        println("Today is $today. Everything is fine.")
    }
}

fun main() {
    prepareDi()// diコンテナにinstance登録。 詳細は割愛

    val something: ISomething = Something()
    something.doSomethingWithDate()
    // 実行結果：Today is {実行日}. Everything is fine.
}

private fun prepareDi() {
    startKoin {
        modules(
            clockModule,
            // testClockModule,
        )
    }
}

private val systemZone = ZoneId.systemDefault()

val clockModule = module {
    single<Clock> { Clock.system(systemZone) }
}

/**
 * もしテストで時刻を固定したいなら、テスト用のインスタンスをDIコンテナに定義する。
 *
 * koinの場合、読み込み順が後のインスタンスで上書されるので、
 * この場合 testClockModule を clockModule より後に差し込めばいい。
 *
 * */
val testClockModule = module {
    single<Clock> {
        /* mockito, mockk等でモックを生成も可 */
        Clock.fixed(
            ZonedDateTime.of(LocalDateTime.of(1999, 1, 1, 0, 0, 0), systemZone).toInstant(),
            systemZone
        )
    }
}
