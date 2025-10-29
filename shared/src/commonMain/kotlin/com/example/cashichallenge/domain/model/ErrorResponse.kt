package com.example.cashichallenge.domain.model

enum class ErrorCodes {
    UNKNOWN_ERROR, SERVER_ERROR, CLIENT_ERROR
}

data class ErrorResponse(val message: String, val code: ErrorCodes)
