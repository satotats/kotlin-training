# 前提

Java/Kotlinのジェネリクスにおける変性は、**「不変」**  
String型はObject型のサブタイプだが、  
List<Object>へのList<String>の代入、またその逆は言語仕様として不可。

これは以下のような型安全でない操作を防止するための仕様。

```kotlin
var superList = mutableList<Any>
var subList = mutableList<String>

superList = subList // compile error(Type mismatch)

superList.add(1) // エラーによって阻止される操作
/**
 * 解説：
 * superListはAny型の要素を扱うListとして宣言されたため
 * インターフェースの定義上は、Int型など他のサブクラスの要素も格納しうる。
 *
 * この変数に List<String> など特定のサブクラスの要素を格納するListを代入できてしまうと、
 * List<String>にIntの要素を格納するような操作が可能になる。
 *
 * これは型安全ではない。
 * → Java/Kotlinがこういった代入を禁止する背景
 * */
```

この逆、List<String>の変数にList<Object>を代入するような操作も、型安全ではないケースがあるため制限されている。

# kotlin in/out

他方、一部のケースでは、前述のような代入操作が許可されてもいい場合がある。

kotlinのジェネリクスにおけるin/out宣言は、  
型安全を脅かす用途でジェネリクス型を使わないことを宣言することで  
ジェネリクスの利用制約を**例外的に**緩めるためにある(「不変」の変性を変化させる)

たとえば以下、任意のサブクラスのListの中身をスーパークラスのListに移し替えるロジックは、言語の変性上コンパイルエラーとなる。
```kotlin
fun main() {
    val subList = mutableListOf<String>()
    val superList = mutableListOf<Any>()
    /* 中略 */
    copy(subList, superList) // compile error(@subList) - Required: MutableList<Any> Found: MutableList<String>
}

fun copy(from: MutableList<Any>, to: MutableList<Any>) {
    from.forEach { to.add(it) } 
}
```
これは前述のとおり、言語として型安全を保証するため  
List<Any>へのList<String>の代入、のようなケースが制限されているからである。  

しかし、上記のロジックは、実行したところで問題にならない。  
copyメソッドによるコピー先の型がList<Any>型である限り、いかなる型のListをコピー元に指定できてよいはず。  
  

## out
言語が防ぎたかったエラーを振り返る。  
エラーはジェネリクス型の保持クラス、がジェネリクス要素を引数に取る場合に発生する。  
はじめの例でいえば、List型のList#addの呼び出しにおいてエラーとなる。  
  
いいかえれば、  
エラーはジェネリクス型の保持クラスにジェネリクス要素を書き込む(Write)ときに発生するものであり、  
その逆、ジェネリクス要素の取り出し(Read)のときには、エラーは発生しない。
```kotlin
var superList = mutableList<Any>
var subList = mutableList<String>

superList = subList
superList.first() // ひとつめの要素を取得 
/**
 * superList = subList 行でコンパイルエラーとなるが、
 * 代入後の用途がreadに限定されるなら、
 * コンパイルエラーが無くとも、問題は発生しない。
 * 
 * superListにString型のsubListが代入された場合、
 * superList#getはつねにAny型のサブクラス(String)を返す。
 * これはsuperListのインターフェース定義と矛盾しない。
 * 
 * */
```

kotlinにおける`out`宣言は、  
この「read用途においては問題が発生しない」という前提をふまえ  
「このジェネリクス型はread(out)用途でしか使わない」ことを宣言することで  
List<SuperClass>へのList<SubClass>に代表されるような代入制約を緩めるために利用する。
```kotlin
fun main() {
    val subList = mutableListOf<String>()
    val superList = mutableListOf<Any>()
    /* 中略 */
    copy(subList, superList) // compile errorとならない
}

fun copy(from: MutableList<out Any>, to: MutableList<Any>) {
    from.forEach { to.add(it) } 
}
```

