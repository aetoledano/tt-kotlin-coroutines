import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    repeat(1_000_000) { // launch a lot of Threads 
        Thread {
            Thread.sleep(3000L)
        }.start()
    }
    
    return
}
