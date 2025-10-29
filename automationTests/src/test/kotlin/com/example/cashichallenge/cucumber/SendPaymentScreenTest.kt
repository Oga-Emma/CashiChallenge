package com.example.cashichallenge.cucumber

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.randomizers.text.StringRandomizer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
import java.net.URI
import java.time.Duration
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SendPaymentAppiumTest {
    private lateinit var driver: AndroidDriver

    @BeforeAll
    fun setUp(){
        val options = UiAutomator2Options()
            .setPlatformName("Android")
            .setPlatformVersion("16.0")
            .setAutomationName("UiAutomator2")
            .setDeviceName("sdk_gphone64_arm64")
            .setUdid("emulator-5554")
            .setAppPackage("com.example.cashichallenge")
            .setAppActivity("com.example.cashichallenge.MainActivity")

        driver = AndroidDriver(URI.create("http://127.0.0.1:4723").toURL(), options)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
    }

    @AfterAll
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun shouldSendPayment_and_update_transaction_list() {
        // Verify state
        driver.findElement(By.ByXPath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button")).click()

        val paymentInfo = generateRandomPaymentInfo()

        val  initialListItems = driver.findElements(By.ByXPath("//*[@text='${paymentInfo.recipientEmail}']"))
        assertTrue(initialListItems.isEmpty())

        // Navigate back
        driver.navigate().back()

        // Navigate to send payment
        driver.findElement(By.ByXPath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.widget.Button")).click()

        val emailField = driver.findElement(By.ByXPath("//android.widget.ScrollView/android.widget.EditText[1]"))
        emailField.sendKeys(paymentInfo.recipientEmail)

        val amountField = driver.findElement(By.ByXPath("//android.widget.ScrollView/android.widget.EditText[2]"))
        amountField.sendKeys(paymentInfo.amount.toString())

        val currencyField = driver.findElement(By.ByXPath("//android.widget.ScrollView/android.widget.EditText[3]"))
        currencyField.sendKeys(paymentInfo.currency)

        val sendPaymentButton = driver.findElement(By.ByXPath("//android.widget.ScrollView/android.view.View[2]/android.widget.Button"))
        sendPaymentButton.click()

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        // Verify that the transaction list is updated*/
        driver.findElement(By.ByXPath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button")).click()

        val  finalListItems = driver.findElements(By.ByXPath("//*[@text='${paymentInfo.recipientEmail}']"))
        assertTrue(finalListItems.isNotEmpty())
        assertTrue(finalListItems.first().toString().contains(paymentInfo.recipientEmail))
    }

    fun generateRandomPaymentInfo(): PaymentInfo {
        val parameters = EasyRandomParameters()
            .randomize(String::class.java, StringRandomizer(10))
            .collectionSizeRange(1, 3)

        val generator = EasyRandom(parameters)

        return generator.nextObject(PaymentInfo::class.java).copy(
            recipientEmail = "user${System.currentTimeMillis()}@example.com",
            currency = "EUR"
        )
    }
}

data class PaymentInfo(
    val recipientEmail: String,
    val amount: Double,
    val currency: String
)
