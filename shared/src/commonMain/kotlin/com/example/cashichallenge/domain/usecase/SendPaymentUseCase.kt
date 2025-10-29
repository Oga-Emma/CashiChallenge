package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.local.Cache
import com.example.cashichallenge.data.remote.PaymentRepository
import com.example.cashichallenge.domain.common.Resource
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.model.dto.toRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.orandja.either.Left
import net.orandja.either.Right

class SendPaymentUseCase(
    private val paymentRepository: PaymentRepository,
    private val cache: Cache
) {
    operator fun invoke(sendPaymentDto: SendPaymentDto): Flow<Resource<Transaction>> = flow {
        emit(Resource.Loading())
        val request = sendPaymentDto.toRequest(cache.getUserId())

        when (val result = paymentRepository.initiatePayment(request)) {
            is Left -> emit(Resource.Error(result.left.message))
            is Right -> emit(Resource.Success(result.right))
        }
    }
}
