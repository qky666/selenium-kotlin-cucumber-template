package cucumber.steps

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.SPConfig
import io.cucumber.java.es.Cuando
import io.cucumber.java.es.Dado
import io.cucumber.java.es.Entonces
import org.apache.logging.log4j.kotlin.Logging
import pom.mainFramePage
import pom.servicesPage

class MtpStepsDefinition: Logging {
    @Dado("Se accede a la web de MTP")
    fun accessMainURl() {
    Selenide.open("")
        mainFramePage.shouldLoadRequired()
    }

    @Dado("Se aceptan la cookies")
    fun acceptCookies() {
        mainFramePage.cookiesBanner.acceptCookies()
        logger.info { "Cookies accepted" }
    }

    @Cuando("Se navega a Servicios -> Aseguramiento de la calidad")
    fun navigateToQualityAssurance() {
        if (SPConfig.pomVersion.equals("mobile", true)) {
            mainFramePage.mobileMenuButton.click()
            mainFramePage.mobileMenu.shouldLoadRequired()
            mainFramePage.mobileMenu.shouldBeCollapsed()
            mainFramePage.mobileMenu.services.click()
            mainFramePage.mobileMenu.servicesQualityAssurance.shouldBe(Condition.visible).click()
        } else {
            // pomVersion = "desktop"
            mainFramePage.mainMenu.services.hover()
            mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        }
    }

    @Entonces("Se carga la página Aseguramiento de la calidad")
    fun qualityAssurancePageIsLoaded() {
        servicesPage.shouldLoadRequired()
    }

    @Entonces("El mensaje de aviso de las cookies no se muestra")
    fun cookiesMessageNotVisible() {
        mainFramePage.cookiesBanner.self.shouldNotBe(Condition.visible)
    }
}
