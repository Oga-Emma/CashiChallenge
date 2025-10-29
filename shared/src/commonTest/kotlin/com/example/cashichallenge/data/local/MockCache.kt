package com.example.cashichallenge.data.local

import com.example.cashichallenge.domain.util.Constants

class MockCache: Cache {
    override fun getUserId(): String {
        return Constants.USER_ID
    }
}