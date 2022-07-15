package cucumber.runner

import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.SPConfig
import io.cucumber.testng.AbstractTestNGCucumberTests
import io.cucumber.testng.CucumberOptions
import org.apache.logging.log4j.kotlin.Logging
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Parameters
import org.testng.annotations.Test

@Test
@CucumberOptions(
    features = ["src/test/resources/features"],
    tags = "@desktop",
    glue = ["cucumber.steps"],
    plugin = [
        "cucumber.listener.ScreenshotCucumberListener",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "pretty",
        "json:build/test-results/json/cucumber_desktop.json",
        "rerun:build/test-results/cucumber/desktop_failed_scenarios.txt"
    ]
)
class CucumberTestNgDesktopTestRunner : AbstractTestNGCucumberTests(), Logging {
    @BeforeMethod(description = "Open base URL in desktop browser", alwaysRun = true)
    @Parameters("browser")
    fun beforeMethod(browser: String = "chrome") {
        SPConfig.setupBasicDesktopBrowser(browser)
        // Open URL
        Selenide.open("")
        logger.info { "URL opened. Browser: $browser"}
    }
}
