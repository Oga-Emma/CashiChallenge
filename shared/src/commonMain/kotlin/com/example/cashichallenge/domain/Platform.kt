package com.example.cashichallenge.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
