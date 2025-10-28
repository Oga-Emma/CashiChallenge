package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.local.Cache
import com.example.cashichallenge.data.remote.PaymentApi
import com.example.cashichallenge.domain.common.Resource
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.model.dto.toRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendPaymentUseCase(
    private val paymentApi: PaymentApi,
    private val cache: Cache
) {
    operator fun invoke(sendPaymentDto: SendPaymentDto): Flow<Resource<Transaction>> = flow {
        try {
            emit(Resource.Loading())

            val request = sendPaymentDto.toRequest(cache.getUserId())
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
