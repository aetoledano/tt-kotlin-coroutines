import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

fun main(args: Array<String>) = runBlocking {
    launch {
        repeat(20) {
            println(it)
            delay(1000L)
        }
    }
    launch{
        good("Mike", 3000L)
    }
    launch {
        good("Iye", 2000L)
    }
    launch {
        good("Itza", 8000L)
    }
    launch{
        bad(5000)
    }
    launch {
        good("He never got a chance",6000)
    }
    
    return@runBlocking
}

suspend fun good(name: String, time: Long) {
    println("$name has started")
    delay(time)
    println("$name has completed")
}

suspend fun bad(time: Long) {
    println("BOMB RUNNING")
    delay(time)
    throw Exception("exploding ...")
}
