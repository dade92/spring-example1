package com.springexample.webapp

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PathController {

    @GetMapping("path/{userId}")
    fun path(@PathVariable userId: Long): ResponseEntity<PathResponse> =
        ResponseEntity.ok(PathResponse(userId))

}

data class PathResponse(
    val id: Long
)