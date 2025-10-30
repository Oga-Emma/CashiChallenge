package com.example.cashichallenge.core.model

import androidx.compose.runtime.Immutable

@Immutable
data class UiDataState<T> (
    val loading: Boolean = false,
    val error: String? = null,
    val data: T? = null
)
