package com.example.cashichallenge.send_payment

import app.cash.turbine.test
import com.example.cashichallenge.domain.common.Resource
import com.example.cashichallenge.domain.common.SendPaymentFormState
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.usecase.SendPaymentUseCase
import com.example.cashichallenge.domain.usecase.ValidatePaymentFormUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class SendPaymentViewModelTest {

    private lateinit var sendPaymentUseCase: SendPaymentUseCase
    private lateinit var validatePaymentFormUseCase: ValidatePaymentFormUseCase
    private lateinit var viewModel: SendPaymentViewmodel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        sendPaymentUseCase = mockk()
        validatePaymentFormUseCase = mockk()
        viewModel = SendPaymentViewmodel(sendPaymentUseCase, validatePaymentFormUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when form is invalid, should update state and send error event`() = runTest {
        // Given
        val invalidDto = SendPaymentDto("", 0.0, "")
        val formErrors = SendPaymentFormState(recipientError = "Error")
        every { validatePaymentFormUseCase(invalidDto) } returns formErrors

        // When
        viewModel.onAction(SendPaymentAction.SendPayment(invalidDto))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(formErrors, viewModel.sendPaymentState.value.formState)
        viewModel.events.test {
            assertEquals(SendPaymentEvent.ShowError("Please fix the validation errors"), awaitItem())
        }
    }

   @Test
    fun `when payment is successful, should update state and send success event`() = runTest {
        // Given
        val validDto = SendPaymentDto("test@test.com", 100.0, "NGN")
        val transaction = Transaction()

        every { validatePaymentFormUseCase(validDto) } returns SendPaymentFormState()
        coEvery { sendPaymentUseCase(validDto) } returns flowOf(
            Resource.Loading(),
            Resource.Success(transaction)
        )

        // When
        viewModel.onAction(SendPaymentAction.SendPayment(validDto))
        
        // Then
        viewModel.sendPaymentState.test {

            var nextState = awaitItem()
            assertEquals(false, nextState.isLoading)

            nextState = awaitItem()
            assertEquals(true, nextState.isLoading)

            testDispatcher.scheduler.advanceUntilIdle() // Process the flow

            nextState = awaitItem()
            assertEquals(false, nextState.isLoading)
        }

       viewModel.events.test {
           assertEquals(SendPaymentEvent.PaymentSentSuccessfully, awaitItem())
       }
    }

    @Test
    fun `when payment fails, should update state and send error event`() = runTest {
        // Given
        val validDto = SendPaymentDto("test@test.com", 100.0, "NGN")
        val errorMessage = "Insufficient funds"

        every { validatePaymentFormUseCase(validDto) } returns SendPaymentFormState()
        every { sendPaymentUseCase(validDto) } returns flowOf(
            Resource.Loading(),
            Resource.Error(errorMessage)
        )

        // When
        viewModel.onAction(SendPaymentAction.SendPayment(validDto))

        // Then
        viewModel.sendPaymentState.test {

            var nextState = awaitItem()
            assertEquals(false, nextState.isLoading)

            nextState = awaitItem()
            assertEquals(true, nextState.isLoading)

            testDispatcher.scheduler.advanceUntilIdle() // Process the flow

            nextState = awaitItem()
            assertEquals(false, nextState.isLoading)
        }

        viewModel.events.test {
            assertEquals(SendPaymentEvent.ShowError(errorMessage), awaitItem())
        }
    }
}
