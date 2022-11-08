package cucumber.runner

import io.cucumber.testng.CucumberOptions
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@Test
@CucumberOptions(
    features = ["@build/test-results/cucumber/failed_scenarios.txt"],
    glue = ["cucumber.steps"],
    plugin = [
        "cucumber.listener.CucumberListener",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "pretty",
        "json:build/test-results/cucumber/rerun.json",
        "rerun:build/test-results/cucumber/failed_again_scenarios.txt"
    ]
)
class CucumberTestNgFailedMobileTestRunner : CucumberTestNgTestRunnerBase() {
    private val mobile = true
    private val browser = "chrome"

    @BeforeMethod(description = "Open base URL in mobile browser", alwaysRun = true)
    fun beforeMethod() {
        setupAndOpenBrowser(browser, mobile)
    }

    @BeforeClass(description = "Write environment variables to Allure report", alwaysRun = true)
    fun beforeClass() {
        storeEnvironmentProperties(browser, mobile)
    }
}
