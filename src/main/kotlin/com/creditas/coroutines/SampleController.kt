package com.creditas.coroutines

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController(
    val srv: SamplerService
) {
    
    @GetMapping("/{resId}")
    suspend fun getResource(
        @PathVariable resId: String
    ): String {
        return srv.getSample(resId).receive()
    }
}
