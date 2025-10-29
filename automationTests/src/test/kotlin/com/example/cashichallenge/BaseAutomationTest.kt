package com.example.cashichallenge

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import java.net.URI
import java.time.Duration

open class BaseAutomationTest {
    protected lateinit var driver: AndroidDriver

    fun launchApp(){
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

    fun cleanup() {
        driver.quit()
    }
}
