package com.example.cashichallenge.data.mapper

import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import kotlin.test.Test
import kotlin.test.assertEquals

class SendPaymentRequestMapperTest {

    @Test
    fun `from should map SendPaymentDto to InitiatePaymentRequest`() {
        // Given
        val mapper = SendPaymentRequestMapper()
        val userId = "testUser123"
        val sendPaymentDto = SendPaymentDto(
            recipientEmail = "test@example.com",
            amount = 250.0,
            currency = "USD"
        )

        // When
        val result = mapper.from(userId, sendPaymentDto)

        // Then
        assertEquals(userId, result.senderId)
        assertEquals(sendPaymentDto.recipientEmail, result.recipientEmail)
        assertEquals(sendPaymentDto.amount, result.amount)
        assertEquals(sendPaymentDto.currency, result.currency)
    }
}
