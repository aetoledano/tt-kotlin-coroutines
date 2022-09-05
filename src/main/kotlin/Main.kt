import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {// coroutine builder
    for (i in 1..10)
        launch { // coroutine builder inherits context
            delay(1000L) // non-blocking suspending function
            println("I'm -$i- in thread ${Thread.currentThread().name}")
        }
}
