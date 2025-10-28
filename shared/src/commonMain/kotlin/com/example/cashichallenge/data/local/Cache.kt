package com.example.cashichallenge.data.local

import com.example.cashichallenge.domain.util.Constants

interface Cache {
    fun getUserId(): String
}

class InMemoryCache(): Cache {
    override fun getUserId(): String {
        return Constants.USER_ID
    }
}
