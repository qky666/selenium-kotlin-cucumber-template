package pom

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.Required
import com.github.qky666.selenidepom.RequiredError
import java.time.Duration
import kotlin.Throws

class ServicesPage : MainFramePage() {
    @Required val principal = Selenide.element("div.servicios-principal")
    @Required val title = Selenide.element("h1.h2")

    @Throws(RequiredError::class)
    override fun shouldLoadRequired(timeout: Duration, pomVersion: String) {
        super.shouldLoadRequired(timeout, pomVersion)
        title.shouldHave(Condition.text("Aseguramiento de la calidad"), timeout)
    }
}

val servicesPage = ServicesPage()
