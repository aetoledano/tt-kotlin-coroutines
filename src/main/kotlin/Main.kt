import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    for (i in 1..10)
        launch(Dispatchers.Default) {
            println("I'm -$i- in thread ${Thread.currentThread().name}")
            delay(1000L)
            println("I'm -$i- in thread ${Thread.currentThread().name}")
        }
}
