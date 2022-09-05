import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    // runBlocking blocks the current thread
    anotherScope()
    
    return@runBlocking
}

suspend fun anotherScope() = coroutineScope {
    // coroutineScope builder is non-blocking
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}
