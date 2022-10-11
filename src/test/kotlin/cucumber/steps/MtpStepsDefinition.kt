package cucumber.steps

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.config.SPConfig
import com.github.qky666.selenidepom.pom.shouldLoadRequired
import io.cucumber.java.es.Cuando
import io.cucumber.java.es.Dado
import io.cucumber.java.es.Entonces
import org.apache.logging.log4j.kotlin.Logging
import org.testng.Assert
import pom.mainFramePage
import pom.searchresult.searchResultsPage
import pom.servicesPage

class MtpStepsDefinition: Logging {
    @Dado("Se accede a la web de MTP")
    fun accessMainURl() {
    Selenide.open("")
        mainFramePage.shouldLoadRequired()
    }

    @Dado("Se aceptan la cookies")
    fun acceptCookies() {
        mainFramePage.acceptCookies()
        logger.info { "Cookies accepted" }
    }

    @Cuando("Se navega a Servicios -> Aseguramiento de la calidad")
    fun navigateToQualityAssurance() {
        if (SPConfig.pomVersion.equals("mobile", true)) {
            mainFramePage.mobileMenuButton.click()
            val mobileMenu = mainFramePage.mobileMenu
            mobileMenu.shouldLoadRequired().shouldBeCollapsed()
            mobileMenu.services.click()
            mobileMenu.servicesQualityAssurance.shouldBe(visible).click()
        } else {
            // pomVersion = "desktop"
            mainFramePage.mainMenu.services.hover()
            mainFramePage.mainMenu.servicesPopUp.qualityAssurance.click()
        }
    }

    @Entonces("Se carga la página Aseguramiento de la calidad")
    fun qualityAssurancePageIsLoaded() {
        servicesPage.shouldLoadRequired()
    }

    @Entonces("El mensaje de aviso de las cookies no se muestra")
    fun cookiesMessageNotVisible() {
        mainFramePage.cookiesBanner.shouldNotBe(visible)
    }

    @Cuando("Se busca el término '{}'")
    fun search(search: String) {
        mainFramePage.mainMenu.searchOpen.click()
        mainFramePage.mainMenu.searchMenu.shouldLoadRequired().searchInput.sendKeys(search)
        mainFramePage.mainMenu.searchMenu.doSearch.click()
        mainFramePage.mainMenu.searchMenu.should(disappear)
    }

    @Entonces("El número de resultados para la búsqueda '{}' es {int}")
    fun searchResultsNumber(search: String, results: Int) {
        searchResultsPage.shouldLoadRequired().breadcrumb.activeBreadcrumbItem.shouldHave(exactText("Results: $search"))
        searchResultsPage.shouldLoadRequired().breadcrumb.breadcrumbItems[0].shouldHave(exactText("Home"))
        Assert.assertEquals(searchResultsPage.searchResults.shouldLoadRequired().count(), results)
    }

    @Entonces("Se obtiene un resultado con título '{}' y texto '{}'")
    fun searchResultTitleText(search: String, textBody: String) {
        val result = searchResultsPage.searchResults.filterBy(text(search)).shouldHave(size(1))[0]
        result.title.shouldHave(exactText(search))
        result.text.shouldHave(text(textBody))
    }
}
