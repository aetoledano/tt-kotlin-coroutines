import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@ExperimentalCoroutinesApi
fun main(args: Array<String>) = runBlocking(Dispatchers.Default) {
    val ch = Channel<String>(Channel.BUFFERED)
    
    launch {
        repeat(20) {
            delay(1000)
            print('.')
        }
        ch.close()
    }
    
    launch(Dispatchers.Default) {
        while (!ch.isClosedForSend) {
            println(ch.receive())
            println("Received on ${Thread.currentThread().name}")
        }
    }
    
    launch {
        delay(2000L)
        ch.send("I was delayed by 2s")
    }
    
    launch {
        delay(4000L)
        ch.send("I was delayed by 4s")
    }
    
    launch {
        repeat(10) {
            delay(100)
            ch.send("I can send right away ! $it")
        }
    }
    
    launch {
        repeat(10) {
            delay(200)
            ch.send("I can send right away too! $it")
        }
    }
    
    return@runBlocking
}
