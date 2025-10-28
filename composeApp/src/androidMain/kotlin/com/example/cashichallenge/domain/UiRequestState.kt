package com.example.cashichallenge.domain

import androidx.compose.runtime.Immutable

@Immutable
data class UiRequestState (
    val loading: Boolean = false,
    val error: String? = null,
)
