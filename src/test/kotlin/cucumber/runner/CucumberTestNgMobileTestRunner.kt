package cucumber.runner

import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.SPConfig
import io.cucumber.testng.AbstractTestNGCucumberTests
import io.cucumber.testng.CucumberOptions
import org.apache.logging.log4j.kotlin.Logging
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@Test
@CucumberOptions(
    features = ["src/test/resources/features"],
    tags = "@mobile",
    glue = ["cucumber.steps"],
    plugin = [
        "cucumber.listener.ScreenshotCucumberListener",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "pretty",
        "json:build/test-results/json/cucumber_desktop.json",
        "rerun:build/test-results/cucumber/desktop_failed_scenarios.txt"
    ]
)
class CucumberTestNgMobileTestRunner : AbstractTestNGCucumberTests(), Logging {
    @BeforeMethod(description = "Open base URL in desktop browser", alwaysRun = true)
    fun beforeMethod() {
        SPConfig.setupBasicMobileBrowser()
        // Open URL
        Selenide.open("")
        logger.info { "URL opened in mobile mode"}
    }
}
