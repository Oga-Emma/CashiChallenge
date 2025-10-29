package com.example.cashichallenge.cucumber.runner

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/com/example/cashichallenge/cucumber/features"],
    glue = ["com.example.cashichallenge.cucumber.steps"],
    plugin = ["pretty", "html:build/reports/cucumber.html"]
)
class CucumberTestRunner
