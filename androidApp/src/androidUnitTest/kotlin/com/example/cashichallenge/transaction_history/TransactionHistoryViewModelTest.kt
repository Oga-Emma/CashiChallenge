package com.example.cashichallenge.transaction_history

import app.cash.turbine.test
import com.example.cashichallenge.core.model.UiDataState
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.usecase.GetTransactionsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

) : TestWatcher() {

    override fun starting(description: org.junit.runner.Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: org.junit.runner.Description) {
        Dispatchers.resetMain()
    }
}

@ExperimentalCoroutinesApi
class TransactionHistoryViewModelTest {

    private lateinit var getTransactionsUseCase: GetTransactionsUseCase
    private lateinit var viewModel: TransactionHistoryViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    @Before
    fun setUp() {
        getTransactionsUseCase = mockk()
        viewModel = TransactionHistoryViewModel(getTransactionsUseCase)
    }

    @Test

    fun `fetchTransactions success - emits loading and data states`() = runTest {
        val testTransactions = listOf(
            Transaction(id = "tx1", amount = 100.0),
            Transaction(id = "tx2", amount = 50.0)
        )

        coEvery { getTransactionsUseCase() } returns flowOf(testTransactions)

        viewModel.transactionState.test {
            val firstState = awaitItem()
            assertEquals(UiDataState(), firstState)

            val secondState = awaitItem()
            assertEquals(UiDataState(loading = true, error = null), secondState)

            val finalState = awaitItem()
            assertEquals(UiDataState(loading = false, error = null, data = testTransactions), finalState)
            assertEquals(finalState.data!!.size, 2)

            expectNoEvents()
        }
    }

//
//    @Test
//    fun `fetchTransactions failure - emits loading and error states`() = runTest {
//
//        coEvery { getTransactionsUseCase() } returns flow {
//            throw RuntimeException("Database error")
//        }
//
//        viewModel.transactionState.test {
//            val firstState = awaitItem()
//            assertEquals(UiDataState(), firstState)
//
//            val secondState = awaitItem()
//            assertEquals(UiDataState(loading = true, error = null), secondState)
//
//            val finalState = awaitItem()
//            assertEquals(UiDataState(loading = false, error = "Failed to fetch transactions"), finalState)
//
//            expectNoEvents()
//        }
//
//       /* val collectedStates = mutableListOf<UiDataState<List<Transaction>>>()
//
//        val job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
//            viewModel.transactionState.toList(collectedStates)
//        }
//
//        runCurrent()
//        println(collectedStates)
//
//        assertEquals(3, collectedStates.size)
//        assertEquals(UiDataState(), collectedStates[0])
//        assertEquals(UiDataState(loading = true, error = null), collectedStates[1])
//        assertEquals(UiDataState(loading = false, error = "Failed to fetch transactions"), collectedStates[2])
//
//        job.cancel()*/
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
