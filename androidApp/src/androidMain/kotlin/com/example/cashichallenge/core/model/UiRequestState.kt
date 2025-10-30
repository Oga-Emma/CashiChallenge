package com.example.cashichallenge.core.model

import androidx.compose.runtime.Immutable

@Immutable
data class UiRequestState (
    val loading: Boolean = false,
    val error: String? = null,
)
