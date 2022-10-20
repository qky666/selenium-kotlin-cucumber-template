package pom.common

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.SelenideElement
import com.github.qky666.selenidepom.pom.Required
import com.github.qky666.selenidepom.pom.Widget

class DesktopMenuWidget(self: SelenideElement) : Widget(self) {
    @Required val services = find("li#servicios_menu>a")
    @Required val sectors = find("li#sectores_menu>a")
    @Required val training = find("li#formacion_menu>a")
    @Required val blog = find("li#blog_menu>a")
    @Required val talent = find("li#talento_menu>a")
    @Required val about = find("li.sobre_menu>a")
    @Required val contact = find("li#contacto_menu>a")
    @Required val langEn = findAll("li.individual-menu-idioma>a").findBy(text("en"))
    @Required val langEs = findAll("li.individual-menu-idioma>a").findBy(text("es"))

    // Search
    val searchOpen = find("button#btn-menu")
    val searchMenu = SearchMenuWidget(find("form#search-menu"))

    // Services Menu
    val servicesPopUp = ServicesPopupMenuWidget(find("div.dropdown-servicios"))
}

class SearchMenuWidget(self: SelenideElement) : Widget(self) {
    @Required val searchInput = find("input[name=s]")
    @Required val doSearch = find("button.search-submit")
}

class ServicesPopupMenuWidget(self: SelenideElement) : Widget(self) {
    @Required val qualityAssurance = find("a[data-principal='Aseguramiento de la calidad']")
}