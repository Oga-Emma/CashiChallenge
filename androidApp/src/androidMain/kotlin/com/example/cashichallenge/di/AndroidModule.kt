package com.example.cashichallenge.di

import com.example.cashichallenge.factory.HttpClientFactory
import com.example.cashichallenge.send_payment.SendPaymentViewmodel
import com.example.cashichallenge.transaction_history.TransactionHistoryViewModel
import io.ktor.client.engine.android.Android
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val provideVideModel = module {
    viewModel { TransactionHistoryViewModel(get()) }
    viewModel { SendPaymentViewmodel(get(), get()) }
}

val androidModule = listOf(
    module {
        single {
            HttpClientFactory.create(Android.create())
        }
    },
    provideVideModel
)
