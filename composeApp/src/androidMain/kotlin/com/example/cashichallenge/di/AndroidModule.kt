package com.example.cashichallenge.di

import com.example.cashichallenge.send_payment.SendPaymentViewmodel
import com.example.cashichallenge.transaction_history.TransactionHistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val provideVideModel = module {
    viewModel { TransactionHistoryViewModel(get()) }
    viewModel { SendPaymentViewmodel(get()) }
}

val androidModule = listOf(
    provideVideModel
)
