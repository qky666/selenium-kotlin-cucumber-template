package cucumber.runner

import io.cucumber.testng.CucumberOptions
import org.testng.annotations.*

@Test
@CucumberOptions(
    features = ["src/test/resources/features"],
    tags = "@desktop",
    glue = ["cucumber.steps"],
    plugin = [
        "cucumber.listener.CucumberListener",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "pretty",
        "json:build/test-results/json/cucumber_desktop.json",
        "rerun:build/test-results/cucumber/desktop_failed_scenarios.txt"
    ]
)
class CucumberTestNgDesktopTestRunner : CucumberTestNgTestRunnerBase() {
    private val mobile = false
    @BeforeMethod(description = "Open base URL in desktop browser", alwaysRun = true)
    @Parameters("browser")
    fun beforeMethod(browser: String = "chrome") {
        setupAndOpenBrowser(browser, mobile)
    }

    @BeforeClass(description = "Write environment variables to Allure report", alwaysRun = true)
    @Parameters("browser")
    fun beforeClass(browser: String = "chrome") {
        storeEnvironmentProperties(browser, mobile)
    }
}
