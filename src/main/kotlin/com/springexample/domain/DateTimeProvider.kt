package com.springexample.domain

import java.time.LocalDateTime

interface DateTimeProvider {
    fun get(): LocalDateTime
}

class NowDateTimeProvider: DateTimeProvider {
    override fun get(): LocalDateTime = LocalDateTime.now()
}