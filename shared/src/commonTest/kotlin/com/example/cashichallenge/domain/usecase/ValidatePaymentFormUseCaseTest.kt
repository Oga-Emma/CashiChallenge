package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.model.request.Currency
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.BeforeTest

class ValidatePaymentFormUseCaseTest {

    private lateinit var useCase: ValidatePaymentFormUseCase

    @BeforeTest
    fun setUp() {
        useCase = ValidatePaymentFormUseCase()
    }

    @Test
    fun `when all fields are valid, returns no errors`() {
        // Given
        val validDto = SendPaymentDto(
            recipientEmail = "test@example.com",
            amount = 100.0,
            currency = Currency.NGN
        )

        // When
        val result = useCase(validDto)

        // Then
        assertEquals("", result.recipientError)
        assertEquals("", result.amountError)
        assertEquals("", result.currencyError)
    }

    @Test
    fun `when email is invalid, returns email error`() {
        // Given
        val invalidDto = SendPaymentDto(
            recipientEmail = "invalid-email",
            amount = 100.0,
            currency = Currency.NGN
        )

        // When
        val result = useCase(invalidDto)

        // Then
        assertEquals("Please enter a valid email address.", result.recipientError)
        assertEquals("", result.amountError)
        assertEquals("", result.currencyError)
    }

    @Test
    fun `when amount is zero, returns amount error`() {
        // Given
        val invalidDto = SendPaymentDto(
            recipientEmail = "test@example.com",
            amount = 0.0,
            currency = Currency.NGN
        )

        // When
        val result = useCase(invalidDto)

        // Then
        assertEquals("", result.recipientError)
        assertEquals("Amount must be greater than zero.", result.amountError)
        assertEquals("", result.currencyError)
    }

    @Test
    fun `when currency is blank, returns currency error`() {
        // Given
        val invalidDto = SendPaymentDto(
            recipientEmail = "test@example.com",
            amount = 100.0,
            currency = null
        )

        // When
        val result = useCase(invalidDto)

        // Then
        assertEquals("", result.recipientError)
        assertEquals("", result.amountError)
        assertEquals("Please select a currency.", result.currencyError)
    }

    @Test
    fun `when all fields are invalid, returns all errors`() {
        // Given
        val invalidDto = SendPaymentDto(
            recipientEmail = "invalid",
            amount = -10.0,
            currency = null
        )

        // When
        val result = useCase(invalidDto)

        // Then
        assertEquals("Please enter a valid email address.", result.recipientError)
        assertEquals("Amount must be greater than zero.", result.amountError)
        assertEquals("Please select a currency.", result.currencyError)
    }
}
