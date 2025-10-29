package com.example.cashichallenge.di

import com.example.cashichallenge.data.local.Cache
import com.example.cashichallenge.data.local.InMemoryCache
import com.example.cashichallenge.data.remote.PaymentApi
import com.example.cashichallenge.data.remote.PaymentRepository
import com.example.cashichallenge.data.remote.PaymentApiImpl
import com.example.cashichallenge.data.remote.PaymentRepositoryImpl
import com.example.cashichallenge.data.repository.FirebaseTransactionDataSource
import com.example.cashichallenge.data.repository.TransactionDataSource
import com.example.cashichallenge.domain.getPlatform
import com.example.cashichallenge.domain.usecase.GetTransactionsUseCase
import com.example.cashichallenge.domain.usecase.SendPaymentUseCase
import com.example.cashichallenge.factory.getFirestore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    single { getPlatform() }
    single { getFirestore() }

    singleOf(::InMemoryCache) { bind<Cache>() }
    singleOf(::FirebaseTransactionDataSource) { bind<TransactionDataSource>() }
    singleOf(::PaymentApiImpl) { bind<PaymentApi>() }
    singleOf(::PaymentRepositoryImpl) { bind<PaymentRepository>() }
    singleOf(::GetTransactionsUseCase)
    singleOf(::SendPaymentUseCase)
}
