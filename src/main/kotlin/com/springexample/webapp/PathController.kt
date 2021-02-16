package com.springexample.webapp

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PathController {

    @GetMapping("path")
    fun path(): ResponseEntity<Any> = ResponseEntity.noContent().build()

}