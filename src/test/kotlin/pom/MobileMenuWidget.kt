@file:Suppress("MemberVisibilityCanBePrivate")

package pom

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Selectors
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.Required
import com.github.qky666.selenidepom.Widget
import org.openqa.selenium.WebElement


class MobileMenuWidget(override val self: SelenideElement = Selenide.element("div#menu-movil ul.uk-nav")) :
    Widget(self) {
    // First level menu items. I only write one, but there are more
    @Required val services = self.find(Selectors.byXpath(".//li[@aria-expanded]/a[.='Servicios']"))

    // All first level menu items
    val firstLevelMenuItems = self.findAll("li.uk-parent")

    // Second level menú items. I only write one, but there are more
    @Suppress("unused") val servicesQualityAssurance = self.find(Selectors.byXpath(".//a[.='Aseguramiento de la calidad']"))

    @Suppress("unused")
    fun shouldBeCollapsed() {
        firstLevelMenuItems.shouldHave(CollectionCondition.allMatch("All firstLevelMenuItems have aria-expanded=false") { element: WebElement ->
            "false".equals(element.getAttribute("aria-expanded"), ignoreCase = true)
        })
    }
}
