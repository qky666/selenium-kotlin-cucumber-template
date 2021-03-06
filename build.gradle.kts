import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "es.mtp"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

plugins {
    kotlin("jvm") version "1.7.10"
    id("io.qameta.allure") version "2.10.0"
    id("com.github.ben-manes.versions") version "0.42.0"
}

dependencies {
    val cucumberVersion = "7.4.1"

    testImplementation("com.codeborne:selenide:6.6.6")
    testImplementation("com.github.qky666:selenide-pom:0.10.0")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-testng:$cucumberVersion")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm:2.18.1")
    testImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.18.0")
    testImplementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    testImplementation(kotlin("test"))
}

allure {
    version.set("2.18.1")
    adapter {
        frameworks {
            cucumberJvm(6)
        }
    }
}

tasks.test {
    // Uncomment desired line
    useTestNG { suiteXmlFiles = listOf(File("src/test/resources/testngDesktop.xml")) }
    // useTestNG { suiteXmlFiles = listOf(File("src/test/resources/testngMobile.xml")) }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

task<Exec>("allureCombine") {
    commandLine(
        "poetry",
        "run",
        "ac",
        "$buildDir/reports/allure-report/allureReport",
        "--dest",
        "$buildDir/reports/allure-combine",
        "--auto-create-folders",
        "--remove-temp-files"
    )
    setDependsOn(listOf("allureReport"))
}