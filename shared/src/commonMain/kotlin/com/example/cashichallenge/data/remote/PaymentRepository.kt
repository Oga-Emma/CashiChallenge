package com.example.cashichallenge.data.remote

import com.example.cashichallenge.data.repository.TransactionDataSource
import com.example.cashichallenge.domain.model.ErrorCodes
import com.example.cashichallenge.domain.model.ErrorResponse
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import net.orandja.either.Either
import net.orandja.either.Left
import net.orandja.either.Right

interface PaymentRepository {
    suspend fun initiatePayment(request: InitiatePaymentRequest): Either<ErrorResponse, Transaction>
}

class PaymentRepositoryImpl(
    val paymentApi: PaymentApi,
    val transactionDataSource: TransactionDataSource
) : PaymentRepository {
    override suspend fun initiatePayment(request: InitiatePaymentRequest): Either<ErrorResponse, Transaction> {
        try {
            val response = paymentApi.sendPayment(request)

            if (response.success && response.data != null) {
                transactionDataSource.saveTransaction(response.data)
                return Right(response.data)
            } else {
                return Left(
                    ErrorResponse(
                        message = response.message,
                        code = ErrorCodes.SERVER_ERROR
                    )
                )
            }

        } catch (e: Exception) {
            return Left(
                ErrorResponse(
                    message = e.message.toString(),
                    code = ErrorCodes.UNKNOWN_ERROR
                )
            )
        }
    }
}
