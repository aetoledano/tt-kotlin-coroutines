import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Why do u think the previous code was halting
// when the exception was thrown ?

// Let's go concurrent / multithreaded now with dispatchers

fun main(args: Array<String>) = runBlocking {
    launch {
        repeat(20) {
            println(it)
            delay(1000L)
        }
    }
    
    good("Mike", 3000L)
    good("Iye", 2000L)
    good("Itza", 8000L)
    bad(5000)
    good("He never got a chance", 6000)
    
    return@runBlocking
}

suspend fun good(name: String, time: Long) = CoroutineScope(Dispatchers.Default).launch {
    println("$name has started")
    delay(time)
    println("$name has completed")
}

suspend fun bad(time: Long) = CoroutineScope(Dispatchers.Default).launch {
    println("BOMB RUNNING")
    delay(time)
    throw Exception("exploding ...")
}
