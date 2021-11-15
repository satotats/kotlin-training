package com.satotats.exercise.kotlin.seald

/** enumもどき */
class Greeting {
    object Hello
    object GoodNight
}

/** interfaceを継承してみたenum */
enum class GreetingEnum : IGreeting {
    Hello {
        override fun greet() {
            println("hello")
        }
    },
    GoodNight {
        override fun greet() {
            println("good night")
        }
    }
}

interface IGreeting {
    fun greet()
}

/** inner classとして書くと、それこそenumの拡張っぽい記法になる */
sealed class Pokemon {
    object Pikachu : Pokemon()
    object Pichu : Pokemon()
    data class Ditto(val pokemon: Pokemon) : Pokemon()
}

/** オブジェクトやクラスとして書くと、これまでinterfaceの実装などで実現していた状態表現の、より論理的に穴の少ないversionといった感じ */
object Zenigame : Pokemon() {
    fun mizudeppo() = println("ミズデッポ ﾌﾟｼｬｱwwwww")
}

class Nidoran(val sex: Sex) : Pokemon()

enum class Sex { `♂`, `♀` }

/** お好きにgotchaしような */
fun gotcha(): Pokemon = Nidoran(Sex.`♂`)

fun main() {
    val p = gotcha()

    when (p) {
        is Pokemon.Pikachu -> println("ゲットでチュウ")
        is Pokemon.Pichu -> println("ゲットでチュウ part2")
        is Pokemon.Ditto -> println("${p.pokemon} かと思ったらメタモンやんけ")
        is Zenigame -> p.mizudeppo()
        is Nidoran -> println("ニドラン${p.sex}やったわ")
        // ファイル外で定義が追加されえないので、
        // ファイル内の状態を網羅した時点でコンパイルエラーが消える(elseいらない)
        // 各分岐ではスマートキャストが効いており、各クラス特有の関数やフィールドもキャスト無しで呼べる
    }
}

/**
 * @see https://kotlinlang.org/docs/sealed-classes.html
 * */