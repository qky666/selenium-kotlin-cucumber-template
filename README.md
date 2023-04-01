# selenium-kotlin-cucumber-template
Template for Selenium-Kotlin-Cucumber automation project

## Requirements
### Kotlin
Documentation: https://kotlinlang.org/

Installed with IntelliJ IDEA

### Python
Documentation: https://www.python.org/

Required by allure-combine.


## Core technologies
### Test framework
TestNG: https://testng.org/doc/index.html

### Logs
Log4j: https://logging.apache.org/log4j/2.x/#

### Report
Allure: https://docs.qameta.io/allure-report/

allure-combine: https://github.com/MihanEntalpo/allure-single-html-file

## Selenium
Selenide: https://selenide.org/index.html

Selenide-POM: https://github.com/qky666/selenide-pom

## BDD
Cucumber: https://cucumber.io/

## TODO
- Test data: ¿JSON? ¿Data class? ¿Properties? Maybe Properties
- Cloud: LambdaTest
- CI/CD: Azure Devops

## Known issues

Allure reports do not work well if the same `.feature` file is used in more than one testng runner class.
With this limitation, you can not run (at once) the same tests on Firefox and on Google Chrome for example. 
You hace to execute a run for each browser separately. 
If you don't need Allure reports, then you can run the same `.feature` more tan once at a time without restrictions.
