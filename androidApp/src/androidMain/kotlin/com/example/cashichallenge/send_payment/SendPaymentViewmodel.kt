package com.example.cashichallenge.send_payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashichallenge.domain.common.Resource
import com.example.cashichallenge.domain.common.SendPaymentFormState
import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.usecase.SendPaymentUseCase
import com.example.cashichallenge.domain.usecase.ValidatePaymentFormUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SendPaymentViewmodel (
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val validatePaymentFormUseCase: ValidatePaymentFormUseCase
) : ViewModel() {

    private val _events = Channel<SendPaymentEvent>()
    val events = _events.receiveAsFlow()

    private val _sendPaymentState = MutableStateFlow(SendPaymentScreenState())
    val sendPaymentState = _sendPaymentState

    fun onAction(action: SendPaymentAction) {
        when (action) {
            is SendPaymentAction.SendPayment -> sendPayment(action.data)
        }
    }

    private fun sendPayment(sendPaymentDto: SendPaymentDto) {
        viewModelScope.launch {
            sendPaymentState.emit(
                _sendPaymentState.value.copy(
                    isLoading = true,
                )
            )
        }

        val sendPaymentFormState = validatePaymentFormUseCase(sendPaymentDto)

        viewModelScope.launch {
            sendPaymentState.emit(
                _sendPaymentState.value.copy(
                    formState = sendPaymentFormState,
                    isLoading = false,
                )
            )
        }

        when (sendPaymentFormState.isValid) {
            false -> viewModelScope.launch {
                sendEvent(
                    SendPaymentEvent.ShowError(
                        "Please fix the validation errors"
                    )
                )
            }

            true -> sendPaymentUseCase(sendPaymentDto).onEach { result ->

                when (result) {
                    is Resource.Loading -> sendPaymentState.emit(
                        _sendPaymentState.value.copy(
                            isLoading = true
                        )
                    )

                    is Resource.Error -> {
                        sendPaymentState.emit(
                            _sendPaymentState.value.copy(
                                isLoading = false
                            )
                        )

                        sendEvent(
                            SendPaymentEvent.ShowError(
                                result.message ?: "Something went wrong, please try again"
                            )
                        )
                    }

                    is Resource.Success -> {
                        sendPaymentState.emit(
                            _sendPaymentState.value.copy(
                                isLoading = false
                            )
                        )
                        sendEvent(
                            SendPaymentEvent.PaymentSentSuccessfully
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }


    }

    fun sendEvent(event: SendPaymentEvent) = viewModelScope.launch {
        _events.send(event)
    }
}

data class SendPaymentScreenState(
    val formState: SendPaymentFormState = SendPaymentFormState(),
    val isLoading: Boolean = false,
)

sealed class SendPaymentAction {
    data class SendPayment(val data: SendPaymentDto) : SendPaymentAction()
}

sealed interface SendPaymentEvent {
    data class ShowError(val error: String) : SendPaymentEvent
    data object PaymentSentSuccessfully : SendPaymentEvent
}
