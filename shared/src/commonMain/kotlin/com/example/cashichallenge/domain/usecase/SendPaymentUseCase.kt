package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.remote.PaymentApi
import com.example.cashichallenge.domain.common.Resource
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendPaymentUseCase(
    private val paymentApi: PaymentApi
) {
    operator fun invoke(request: InitiatePaymentRequest): Flow<Resource<Transaction>> = flow {
        try {
            emit(Resource.Loading())
            val response = paymentApi.initiatePayment(request)

            if (response.success && response.data != null) {
                emit(Resource.Success(response.data))
            } else {
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}
