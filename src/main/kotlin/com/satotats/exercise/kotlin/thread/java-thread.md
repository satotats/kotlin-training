# Thread Basics(Java)
一般的なコンピュータ(というかOS)が、アプリケーションを「スレッド」という単位で処理していく、という知識がある前提

## (Java)Thread
- javaにおいて、すべての処理は「スレッド」というまとまりで実行される
- javaは最低ひとつのスレッドを持つ。この「最低1つ」のスレッドをメインスレッドという
- メインスレッドはjvmがプログラムを実行する(mainメソッドを呼び出す)と同時に作成される
>Even if you've never worked directly with Java threads, you've worked indirectly with them because Java's main() method contains a main Thread. Anytime you've executed the main() method, you've also executed the main Thread. [refs](https://www.infoworld.com/article/3336222/java-challengers-6-thread-behavior-in-the-jvm.html)
- すべてのJavaスレッドは、java.lang.Threadクラスを実装したインスタンスの形でjvmに管理される

### Note
参考：ktor applicationを起動したときにlogbackが吐くログ
```
22:53:15.079 [main] INFO  Application - Application started in 0.803 seconds.
```
角括弧内の`main`が、メインスレッドで実行されていることを示している(logbackの標準的なログフォーマットの場合)

## Native thread
- javaが管理するスレッドにたいして、OSが管理するスレッドをNative Threadと呼ぶ
> Threading is usually supported down to the operating system. We call threads that work at this level “native threads”.[refs](https://www.baeldung.com/java-threading-models)
- ひとつのJavaスレッドには、ひとつのNativeスレッドが紐づけられる
- Java内でいかなるスレッド管理のアプローチをとったとしても、背後では必ずNative Threadが実行される

- Nativeスレッドの処理タイミングはOSによって管理される
- 実行タイミングなど、java側からより細やかなスレッド管理が可能になるGreen Threadという実装方式も存在したが、めちゃめちゃ前(Java 1.2?)にサポートが止まった

> The OS divides processing time not only among different applications, but also among each thread within an application. [refs](https://www.tutorialspoint.com/java/java_multithreading.htm)
