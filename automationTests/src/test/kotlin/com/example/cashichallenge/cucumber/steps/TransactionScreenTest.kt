package com.example.cashichallenge.cucumber.steps

import com.example.cashichallenge.BaseAutomationTest
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import kotlin.test.assertTrue

class TransactionScreenStepDefinitions : BaseAutomationTest() {
    private lateinit var transactionButton: WebElement

    @Given("User opens the app 2")
    fun user_opens_the_app_2() {
        launchApp()
        transactionButton = driver.findElement(By.ByXPath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button"))
    }

    @When("User clicks view transactions")
    fun user_clicks_view_transaction() {
        // Verify state
        transactionButton.click()
    }

    @Then("App should navigate to transactions screen")
    fun app_should_navigate_to_send_payment_screen() {
        // Verify state
        val sendPaymentScreen = driver.findElement(By.ByXPath("//*[@text='Transactions']"))
        assertTrue(sendPaymentScreen.isDisplayed)
    }
}
