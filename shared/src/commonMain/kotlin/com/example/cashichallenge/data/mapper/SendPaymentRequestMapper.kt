package com.example.cashichallenge.data.mapper

import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest

class SendPaymentRequestMapper {
    fun from(userId: String, sendPaymentDto: SendPaymentDto): InitiatePaymentRequest {
        return InitiatePaymentRequest(
            senderId = userId,
            recipientEmail = sendPaymentDto.recipientEmail!!,
            amount = sendPaymentDto.amount!!,
            currency = sendPaymentDto.currency!!
        )
    }
}