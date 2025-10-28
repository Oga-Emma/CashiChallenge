package com.example.cashichallenge.send_payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashichallenge.domain.UiRequestState
import com.example.cashichallenge.domain.common.Resource
import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import com.example.cashichallenge.domain.usecase.SendPaymentUseCase
import com.example.cashichallenge.domain.util.Constants
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SendPaymentViewmodel(private val sendPaymentUseCase: SendPaymentUseCase) : ViewModel() {

    private val _events = Channel<SendPaymentEvent>()
    val events = _events.receiveAsFlow()

    private val _sendPaymentState = MutableStateFlow(UiRequestState())
    val sendPaymentState = _sendPaymentState

    fun onAction(action: SendPaymentAction) {
        when (action) {
            is SendPaymentAction.SendPayment -> sendPayment(action.data)
        }
    }

    private fun sendPayment(sendPaymentFormState: SendPaymentFormState) {
        sendPaymentUseCase(sendPaymentFormState.toDto()).onEach { result ->
            when (result) {
                is Resource.Loading -> sendPaymentState.emit(UiRequestState(loading = true))
                is Resource.Error -> {
                    val error = result.message ?: "Something went wrong, please try again"
                    sendPaymentState.emit(
                        UiRequestState(
                            loading = false,
                            error = error
                        )
                    )

                    sendEvent(SendPaymentEvent.ShowError(error))
                }

                is Resource.Success -> {
                    sendPaymentState.emit(
                        UiRequestState(loading = false, error = null)
                    )
                    sendEvent(
                        SendPaymentEvent.PaymentSentSuccessfully
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sendEvent(event: SendPaymentEvent) = viewModelScope.launch {
        _events.send(event)
    }
}

sealed class SendPaymentAction {
    data class SendPayment(val data: SendPaymentFormState) : SendPaymentAction()
}

sealed interface SendPaymentEvent {
    data class ShowError(val error: String) : SendPaymentEvent
    data object PaymentSentSuccessfully : SendPaymentEvent
}

data class SendPaymentFormState(
    val recipientEmail: String,
    val amount: Double,
    val currency: String
)

fun SendPaymentFormState.toDto() = SendPaymentDto(
    recipientEmail = recipientEmail,
    amount = amount,
    currency = currency
)
