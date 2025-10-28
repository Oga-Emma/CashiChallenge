package com.example.cashichallenge.domain

class JVMPlatform: Platform {
    override val name: String = ""
}
actual fun getPlatform(): Platform {
    return JVMPlatform()
}
