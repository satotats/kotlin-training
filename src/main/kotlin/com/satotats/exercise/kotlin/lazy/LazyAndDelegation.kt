package com.satotats.exercise.kotlin.lazy

import kotlin.random.Random
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.typeOf


class SomethingFactory {
    fun get(): Lazy<Something> {
        return lazy {
            when (Random.Default.nextInt()) {
                in Int.MIN_VALUE..-1 -> Thing
                0 -> Stuff
                else -> Matter
            }
        }
    }
}

sealed interface Something

object Matter : Something
object Stuff : Something
object Thing : Something

fun main() {
    println("give me something")

    val something by SomethingFactory().get()
    println("gotcha: ${something::class.simpleName}")


    val p: HogeService by getLazy()
    val pp: HogeService = get()
    println(p)
    println(pp)
}


interface HogeService
class HogeSpecialServiceImpl : HogeService

interface ServiceFactory {
    fun getHoge(): HogeService
}

class ServiceFactorySpecial : ServiceFactory {
    override fun getHoge(): HogeService {
        return HogeSpecialServiceImpl()
    }
}


val factory: ServiceFactory = ServiceFactorySpecial()

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> get(): T {
    val function =
        ServiceFactory::class.functions.find { f -> f.returnType == typeOf<T>() } ?: throw Exception("そんなものは ない")

    val gotten = function.javaMethod!!.invoke(factory)
    if (gotten !is T) throw Exception("なにかがおかしいぞい")

    return gotten
}

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> getLazy(): Lazy<T> {
    return lazy { get() }
}

