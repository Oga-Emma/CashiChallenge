package com.example.cashichallenge.cucumber.steps

import com.example.cashichallenge.BaseAutomationTest
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import kotlin.test.assertTrue

class PaymentScreenStepDefinitions : BaseAutomationTest() {
    private lateinit var sendPaymentButton: WebElement

    @Given("User opens the app 1")
    fun user_opens_the_app_1() {
        launchApp()

        sendPaymentButton = driver.findElement(By.ByXPath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.widget.Button"))
    }

    @When("User clicks send payment")
    fun user_clicks_send_payment() {
        // Verify state
        sendPaymentButton.click()
    }

    @Then("App should navigate to send payment screen")
    fun app_should_navigate_to_send_payment_screen() {
        // Verify state
        val sendPaymentScreen = driver.findElement(By.ByXPath("//*[@text='Transfer Money']"))
        assertTrue(sendPaymentScreen.isDisplayed)
    }
}
