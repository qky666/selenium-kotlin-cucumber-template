package cucumber.steps

import com.codeborne.selenide.Selenide
import io.cucumber.java.After
import io.cucumber.java.Scenario
import org.openqa.selenium.OutputType

class CucumberHooks {
    @After
    fun after(scenario: Scenario) {
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
