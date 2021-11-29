package com.springexample.webapp

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("products")
class ProductsProperties {
    lateinit var url: String
}