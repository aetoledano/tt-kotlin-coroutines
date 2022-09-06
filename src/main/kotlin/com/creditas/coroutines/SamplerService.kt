package com.creditas.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class SamplerService {
    
    val channels: HashMap<String, Channel<String>> = HashMap()
    val results: HashMap<String, UUID> = HashMap()
    val waiting: ConcurrentHashMap<String, MutableList<Channel<String>>> = ConcurrentHashMap()
    private final val processingChannel: Channel<String> = Channel(BUFFERED)
    
    init {
        longCalculation()
    }
    
    companion object {
        const val LONG_CALCULUS_TIME = 10_000L
    }
    
    suspend fun getSample(resId: String): Channel<String> {
        val existingChannel = channels[resId]
        
        val ch =
            if (existingChannel == null) {
                val ch = Channel<String>(BUFFERED)
                channels[resId] = ch
                ch
            } else {
                existingChannel
            }
        
        getResult(resId, ch)
        
        return ch
    }
    
    
    private suspend fun getResult(resId: String, ch: Channel<String>) {
        val result = results[resId]
        
        if (result != null) {
            println("Result for $resId is calculated")
            ch.send(result.toString())
            return
        }
    
        val waiters =
        if (waiting[resId] == null) {
            val lst =
                mutableListOf<Channel<String>>()
            
            waiting[resId] = lst
            lst.add(ch)
            processingChannel.send(resId)
            
            lst
        } else {
            val lst = waiting[resId]
            lst?.add(ch)
            
            lst
        }
        
        println("${waiters?.size} clients waiting")
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    final fun longCalculation() =
        CoroutineScope(Dispatchers.IO).launch {
            while (!processingChannel.isClosedForReceive) {
                val resId = processingChannel.receive()
                
                println("Processing $resId")
                
                delay(LONG_CALCULUS_TIME)
                results[resId] = UUID.randomUUID()
                
                val lst = waiting[resId]!!
                
                println("Notifying ${lst.size} clients")
                
                for (ch in lst)
                    ch.send(results[resId].toString())
                
                waiting.remove(resId)
            }
        }
}
