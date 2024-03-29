import ru.vyarus.gradle.plugin.python.task.PythonTask

group = "com.github.qky666"
version = "0.18.4"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

plugins {
    kotlin("jvm") version "1.8.21"
    id("io.qameta.allure") version "2.11.2"
    id("com.github.ben-manes.versions") version "0.46.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.2"
    id("ru.vyarus.use-python") version "3.0.0"
}

dependencies {
    val log4jVersion = "2.20.0"
    val cucumberVersion = "7.12.0"

    testImplementation("org.testng:testng:7.8.0")
    testImplementation("com.github.qky666:selenide-pom:0.18.4")
    testImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-testng:$cucumberVersion")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm:2.22.1")
    testImplementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")
    testImplementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    testImplementation(kotlin("test"))
}

allure {
    version.set("2.20.0")
    adapter {
        frameworks {
            cucumberJvm(6)
        }
    }
}

tasks.test {
    useTestNG { suiteXmlFiles = listOf(File("src/test/resources/testng.xml")) }
}

kotlin {
    jvmToolchain(17)
}

tasks.compileTestKotlin {
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
}

tasks.compileTestJava {
    options.compilerArgs.addAll(listOf("-encoding", "UTF-8"))
}

python {
    pip("allure-combine:1.0.8")
}

task<PythonTask>("allureCombine") {
    command =
        "-m allure_combine.combine $buildDir/reports/allure-report/allureReport --dest $buildDir/reports/allure-combine --auto-create-folders --remove-temp-files"
    dependsOn.add("allureReport")
}
