package cucumber.runner

import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.config.SPConfig
import io.cucumber.testng.AbstractTestNGCucumberTests
import org.testng.annotations.DataProvider
import java.io.File
import java.util.Properties

abstract class CucumberTestNgTestRunnerBase : AbstractTestNGCucumberTests() {
    private val defaultAllureResultsPath = "build/allure-results/"

    @DataProvider(parallel = true)
    override fun scenarios(): Array<out Array<Any>>? {
        return super.scenarios()
    }

    fun setupAndOpenBrowser(browser: String = "chrome", mobile: Boolean = false) {
        if (mobile) {
            SPConfig.setupBasicMobileBrowser()
        } else {
            SPConfig.setupBasicDesktopBrowser(browser)
        }
        Selenide.open("")
    }

    fun storeEnvironmentProperties(browser: String? = null, mobile: Boolean? = null) {
        if (browser == null && mobile == null) {
            return
        }
        val allureProperties = Properties()
        val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("allure.properties")
        if (inputStream != null) {
            allureProperties.load(inputStream)
        }
        val allureResultsDirectory = allureProperties.getProperty("allure.results.directory", defaultAllureResultsPath)
        val environmentPropertiesPath =
            if (allureResultsDirectory.endsWith("/")) "${allureResultsDirectory}environment.properties" else "$allureResultsDirectory/environment.properties"
        val environmentProperties = Properties()
        if (browser != null) {
            environmentProperties.setProperty("Browser", browser)
        }
        if (mobile != null) {
            environmentProperties.setProperty("Mobile", mobile.toString())
        }
        environmentProperties.store(
            File(environmentPropertiesPath).outputStream(), "Environment values for Allure report"
        )
    }
}
