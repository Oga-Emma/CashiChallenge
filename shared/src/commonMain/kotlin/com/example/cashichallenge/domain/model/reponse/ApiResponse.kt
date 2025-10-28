package com.example.cashichallenge.domain.model.reponse

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val message: String,
    val success: Boolean,
    val data: T? = null,
)
