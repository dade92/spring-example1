package com.springexample.webapp

import com.springexample.domain.PathId
import com.springexample.domain.PathUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PathController(
    private val pathUseCase: PathUseCase
) {

    @GetMapping("path/{userId}")
    fun path(@PathVariable userId: Long): ResponseEntity<PathResponse> {
        val path = pathUseCase.retrieve(PathId(userId))
        return ResponseEntity.ok(PathResponse(path.id))
    }

}

data class PathResponse(
    val id: Long
)