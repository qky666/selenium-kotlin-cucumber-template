@file:Suppress("unused")

package cucumber.steps

import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.config.SPConfig
import io.cucumber.java8.Scenario
import io.cucumber.java8.Es

import org.openqa.selenium.OutputType

class CucumberHooks: Es{

    init {
        Before { scenario: Scenario ->
            SPConfig.selenideConfig.browser(getBrowserFromTestName(scenario.name))
            SPConfig.model = getModelFromTestName(scenario.name)
            SPConfig.lang = getLanguageFromTestName(scenario.name)
            if (SPConfig.model.contentEquals("mobile", ignoreCase = true)) {
                SPConfig.setupBasicMobileBrowser()
            } else {
                SPConfig.setupBasicDesktopBrowser()
            }
        }

        After { scenario: Scenario ->
            if (scenario.isFailed) {
                val screenshot = Selenide.screenshot(OutputType.BYTES)
                if (screenshot != null) {
                    scenario.attach(screenshot, "image/png", "Test failed screenshot")
                } else {
                    scenario.log("Scenario is failed but there is no screenshot available in ${scenario.name}")
                }
            }
            Selenide.closeWebDriver()
        }
    }

    private fun getBrowserFromTestName(name: String): String {
        return name.substringAfter("Navegador: ").substringBefore(";")
    }

    private fun getModelFromTestName(name: String): String {
        return name.substringAfter("Modelo: ").substringBefore(";")
    }

    private fun getLanguageFromTestName(name: String): String {
        return name.substringAfter("Idioma: ").substringBefore(";")
    }
}
