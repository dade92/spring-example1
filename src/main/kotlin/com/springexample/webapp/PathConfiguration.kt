package com.springexample.webapp

import com.springexample.domain.PathUseCase
import com.springexample.domain.PathUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class PathConfiguration {

    @Bean
    open fun pathUseCase(): PathUseCase = PathUseCaseImpl()

}