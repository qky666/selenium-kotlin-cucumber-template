package cucumber.listener

import com.codeborne.selenide.Selenide
import io.cucumber.plugin.ConcurrentEventListener
import io.cucumber.plugin.event.EventHandler
import io.cucumber.plugin.event.EventPublisher
import io.cucumber.plugin.event.PickleStepTestStep
import io.cucumber.plugin.event.TestStepFinished
import io.qameta.allure.Allure
import io.qameta.allure.AllureLifecycle
import org.openqa.selenium.OutputType

@Suppress("unused")
class ScreenshotCucumberListener @JvmOverloads constructor(private val lifecycle: AllureLifecycle = Allure.getLifecycle()) :
    ConcurrentEventListener {

    private val stepFinishedHandler = EventHandler { event: TestStepFinished -> handleTestStepFinished(event) }

    /*
    Event Handlers
     */
    override fun setEventPublisher(publisher: EventPublisher) {
        publisher.registerHandlerFor(TestStepFinished::class.java, stepFinishedHandler)
    }

    private fun handleTestStepFinished(event: TestStepFinished) {
        val step = event.testStep
        if (step is PickleStepTestStep) {
            val screenshot = Selenide.screenshot(OutputType.BYTES)
            if (screenshot != null) Allure.addAttachment("After step screenshot", screenshot.inputStream())
        }
    }
}
