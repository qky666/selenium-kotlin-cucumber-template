@file:Suppress("unused")

package cucumber.steps

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.Condition.disappear
import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.config.SPConfig
import com.github.qky666.selenidepom.pom.shouldLoadRequired
import io.cucumber.java8.Es
import org.apache.logging.log4j.kotlin.Logging
import org.testng.Assert
import pom.common.mainFramePage
import pom.pages.home.homePage
import pom.pages.services.servicesPage
import pom.searchresults.searchResultsPage

class MtpStepsDefinition : Es, Logging {

    init {
        Dado("Se accede a la web de MTP") {
            Selenide.open("")
            SPConfig.lang = "es"
            homePage.shouldLoadRequired()
        }

        Dado("Se aceptan las cookies") {
            mainFramePage.acceptCookies()
            logger.info { "Cookies accepted" }
        }

        Cuando("Se navega a Servicios -> Aseguramiento de la calidad") {
            if (SPConfig.model.equals("mobile", true)) {
                mainFramePage.shouldLoadRequired().mobileMenu.mobileMenuButton.click()
                val mobileMenu = mainFramePage.mobileMenuPopUp
                mobileMenu.shouldLoadRequired().shouldBeCollapsed()
                mobileMenu.services().click()
                mobileMenu.servicesQualityAssurance().shouldBe(visible).click()
            } else {
                // model = "desktop"
                mainFramePage.shouldLoadRequired().desktopMenu.services.hover()
                mainFramePage.desktopMenu.servicesPopUp.qualityAssurance.click()
            }
        }

        Entonces("Se carga la página Aseguramiento de la calidad") {
            servicesPage.shouldLoadRequired()
        }

        Entonces("El mensaje de aviso de las cookies no se muestra") {
            mainFramePage.shouldLoadRequired().cookiesBanner.shouldNotBe(visible)
        }

        Cuando("Se busca el término '{}'") { search: String ->
            mainFramePage.shouldLoadRequired().desktopMenu.searchOpen.click()
            mainFramePage.desktopMenu.searchMenu.shouldLoadRequired().searchInput.sendKeys(search)
            mainFramePage.desktopMenu.searchMenu.doSearch.click()
            mainFramePage.desktopMenu.searchMenu.should(disappear)
        }

        Entonces("El número de páginas de resultados para la búsqueda '{}' es {int}") { search: String, resultsPages: Int ->
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

        Cuando("Se navega a la página {int} de resultados de la búsqueda") { page: Int ->
            searchResultsPage.shouldLoadRequired().pagination.shouldLoadRequired().pagesLinks.find(exactText(page.toString()))
                .click()
            searchResultsPage.shouldLoadRequired().pagination.shouldLoadRequired().currentPage.shouldHave(exactText(page.toString()))
        }

        Entonces("La página de resultados mostrada es la última") {
            searchResultsPage.shouldLoadRequired().pagination.nextPage.should(disappear)
            searchResultsPage.pagination.previousPage.shouldBe(visible)
        }

        Entonces("El número de resultados para la búsqueda mostrados es {int}") { results: Int ->
            Assert.assertEquals(
                searchResultsPage.shouldLoadRequired().searchResults.shouldLoadRequired().count(), results
            )
        }

        Entonces("Se muestra un resultado para la búsqueda con título '{}' y texto '{}'") { search: String, textBody: String ->
            val result =
                searchResultsPage.shouldLoadRequired().searchResults.filterBy(text(search)).shouldHave(size(1))[0]
            result.title.shouldHave(exactText(search))
            result.text.shouldHave(text(textBody))
        }
    }
}
