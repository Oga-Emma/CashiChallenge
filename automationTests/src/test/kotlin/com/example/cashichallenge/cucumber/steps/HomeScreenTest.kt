package com.example.cashichallenge.cucumber.steps

import com.example.cashichallenge.BaseAutomationTest
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import kotlin.test.assertTrue

class HomeScreenStepDefinitions : BaseAutomationTest() {
    private lateinit var sendPaymentButton: WebElement
    private lateinit var transactionButton: WebElement

    @Given("User opens the app")
    fun user_opens_the_app() {
        launchApp()

        sendPaymentButton = driver.findElement(By.ByXPath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.widget.Button"))
        transactionButton = driver.findElement(By.ByXPath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button"))


    }

    @Then("Home screen should be visible")
    fun shouldLaunchHomeScreen_whenAppStarts() {
        // Verify state
        assertTrue(sendPaymentButton.isDisplayed)
        assertTrue(transactionButton.isDisplayed)
    }
}
