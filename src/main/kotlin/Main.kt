import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    for (i in 1..10)
        launch {
            delay(i*200L)
            println("world!! $i")
        }
    
    println("Hello ")
}
