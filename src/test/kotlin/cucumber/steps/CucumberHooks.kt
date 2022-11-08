@file:Suppress("unused")

package cucumber.steps

import com.codeborne.selenide.Selenide
import io.cucumber.java8.Scenario
import io.cucumber.java8.Es

import org.openqa.selenium.OutputType

class CucumberHooks: Es{

    init {
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
}
