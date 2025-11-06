plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "app.example.appiumtest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.seleniumhq.selenium:selenium-java:4.38.0")
    testImplementation("io.appium:java-client:10.0.0")
    testImplementation(libs.easy.random.core)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")

    testImplementation("io.cucumber:cucumber-core:7.16.1")
    testImplementation("io.cucumber:cucumber-java:7.16.1")
    testImplementation("io.cucumber:cucumber-junit:7.16.1")
    testImplementation("org.junit.vintage:junit-vintage-engine:6.0.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
