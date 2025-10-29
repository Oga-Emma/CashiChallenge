package com.example.cashichallenge

import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cashichallenge.coreui.ObserveAsEvents
import com.example.cashichallenge.home.HomeScreen
import com.example.cashichallenge.send_payment.SendPaymentAction
import com.example.cashichallenge.send_payment.SendPaymentEvent
import com.example.cashichallenge.send_payment.SendPaymentScreen
import com.example.cashichallenge.send_payment.SendPaymentViewmodel
import com.example.cashichallenge.transaction_history.TransactionHistoryScreen
import com.example.cashichallenge.transaction_history.TransactionHistoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
//
//    val platform = getPlatform()

    val context = LocalContext.current

    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Destinations.HOME) {
            composable(route = Destinations.HOME) {
                HomeScreen(
                    onSendPaymentClick = {
                        navController.navigate(Destinations.SEND_PAYMENT)
                    },
                    onTransactionHistoryClick = {
                        navController.navigate(Destinations.TRANSACTION_HISTORY)
                    }
                )
            }
            composable(route = Destinations.SEND_PAYMENT) {
                val sendPaymentViewmodel: SendPaymentViewmodel = koinViewModel()
                ObserveAsEvents(events = sendPaymentViewmodel.events) { event ->
                    when (event) {
                        is SendPaymentEvent.PaymentSentSuccessfully -> {
                            Toast.makeText(
                                context,
                                "Payment sent successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        }

                        is SendPaymentEvent.ShowError -> {
                            Toast.makeText(
                                context,
                                event.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                SendPaymentScreen(
                    state = sendPaymentViewmodel.sendPaymentState.collectAsStateWithLifecycle().value,
                    onSendPaymentClick = {
                        sendPaymentViewmodel.onAction(
                            SendPaymentAction.SendPayment(it)
                        )
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                )
            }
            composable(
                route = Destinations.TRANSACTION_HISTORY,
            ) {
                val transactionHistoryViewModel: TransactionHistoryViewModel = koinViewModel()
                TransactionHistoryScreen(
                    state = transactionHistoryViewModel.transactionState.collectAsStateWithLifecycle().value,
                    onRetryClick = {
//                        transactionHistoryViewModel.getTransactions()
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}

object Destinations {
    const val HOME = "home"
    const val SEND_PAYMENT = "send_payment"
    const val TRANSACTION_HISTORY = "transaction_history"
}
