package com.example.cashichallenge.transaction_history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashichallenge.data.local.Cache
import com.example.cashichallenge.domain.UiDataState
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.usecase.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger

class TransactionHistoryViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel() {

    private val _transactionState = MutableStateFlow(UiDataState<List<Transaction>>())
    val transactionState = _transactionState
        .onStart {
            fetchTransactions()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiDataState()
        )

    fun fetchTransactions() = viewModelScope.launch {
        getTransactionsUseCase()
            .stateIn(viewModelScope)
            .onStart {
                _transactionState.emit(UiDataState(loading = true, error = null))
            }
            .catch { exception ->
                _transactionState.emit(
                    UiDataState(
                        loading = false,
                        error = "Failed to fetch transactions"
                    )
                )
            }
            .collect {
                _transactionState.emit(
                    UiDataState(
                        data = it,
                        error = null,
                        loading = false,
                    )
                )
            }
    }
}
