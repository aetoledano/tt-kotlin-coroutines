import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    launch {
        yourFirstSuspendingFunction()
    }
    println("hello ")
    
    return@runBlocking
}

suspend fun yourFirstSuspendingFunction() {
    println("from suspending function !")
}
