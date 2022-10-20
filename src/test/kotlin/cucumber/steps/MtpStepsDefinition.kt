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
import pom.common.mainFramePage
import pom.pages.home.homePage
import pom.searchresults.searchResultsPage
import pom.pages.services.servicesPage

class MtpStepsDefinition : Logging {
    @Dado("Se accede a la web de MTP")
    fun accessMainURl() {
        Selenide.open("")
        homePage.shouldLoadRequired().mainBanner.verifyTextsEs()
    }

    @Dado("Se aceptan la cookies")
    fun acceptCookies() {
        mainFramePage.acceptCookies()
        logger.info { "Cookies accepted" }
    }

    @Cuando("Se navega a Servicios -> Aseguramiento de la calidad")
    fun navigateToQualityAssurance() {
        if (SPConfig.pomVersion.equals("mobile", true)) {
            mainFramePage.shouldLoadRequired().mobileMenuButton.click()
            val mobileMenu = mainFramePage.mobileMenu
            mobileMenu.shouldLoadRequired().shouldBeCollapsed()
            mobileMenu.services.click()
            mobileMenu.servicesQualityAssurance.shouldBe(visible).click()
        } else {
            // pomVersion = "desktop"
            mainFramePage.shouldLoadRequired().mainMenu.services.hover()
            mainFramePage.mainMenu.servicesPopUp.qualityAssurance.click()
        }
    }

    @Entonces("Se carga la página Aseguramiento de la calidad")
    fun qualityAssurancePageIsLoaded() {
        servicesPage.shouldLoadRequired()
    }

    @Entonces("El mensaje de aviso de las cookies no se muestra")
    fun cookiesMessageNotVisible() {
        mainFramePage.shouldLoadRequired().cookiesBanner.shouldNotBe(visible)
    }

    @Cuando("Se busca el término '{}'")
    fun search(search: String) {
        mainFramePage.shouldLoadRequired().mainMenu.searchOpen.click()
        mainFramePage.mainMenu.searchMenu.shouldLoadRequired().searchInput.sendKeys(search)
        mainFramePage.mainMenu.searchMenu.doSearch.click()
        mainFramePage.mainMenu.searchMenu.should(disappear)
    }

    @Entonces("El número de páginas de resultados para la búsqueda '{}' es {int}")
    fun searchResultsPagesNumber(search: String, resultsPages: Int) {
        val maxResultsPerPage = 5
        searchResultsPage.shouldLoadRequired().breadcrumb.activeBreadcrumbItem.shouldHave(exactText("Results: $search"))
        searchResultsPage.breadcrumb.breadcrumbItems[0].shouldHave(exactText("Home"))
        Assert.assertEquals(searchResultsPage.searchResults.shouldLoadRequired().count(), maxResultsPerPage)
        searchResultsPage.pagination.shouldLoadRequired().currentPage.shouldHave(exactText("1"))
        searchResultsPage.pagination.nextPage.shouldBe(visible)
        searchResultsPage.pagination.pagesLinks.shouldHave(size(resultsPages))[resultsPages - 2].shouldHave(
            exactText(resultsPages.toString())
        )
    }

    @Cuando("Se navega a la página {int} de resultados de la búsqueda")
    fun navigateToLastPage(page: Int) {
        searchResultsPage.shouldLoadRequired().pagination.shouldLoadRequired().pagesLinks.find(exactText(page.toString()))
            .click()
        searchResultsPage.shouldLoadRequired().pagination.shouldLoadRequired().currentPage.shouldHave(exactText(page.toString()))
    }

    @Entonces("La página de resultados mostrada es la última")
    fun lastPage() {
        searchResultsPage.shouldLoadRequired().pagination.nextPage.should(disappear)
        searchResultsPage.pagination.previousPage.shouldBe(visible)
    }

    @Entonces("El número de resultados para la búsqueda mostrados es {int}")
    fun searchResultsShown(results: Int) {
        Assert.assertEquals(searchResultsPage.shouldLoadRequired().searchResults.shouldLoadRequired().count(), results)
    }

    @Entonces("Se muestra un resultado para la búsqueda con título '{}' y texto '{}'")
    fun searchResultTitleText(search: String, textBody: String) {
        val result = searchResultsPage.shouldLoadRequired().searchResults.filterBy(text(search)).shouldHave(size(1))[0]
        result.title.shouldHave(exactText(search))
        result.text.shouldHave(text(textBody))
    }
}
