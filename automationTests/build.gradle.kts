plugins {
    alias(libs.plugins.kotlinJvm)
}
/*
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }

    dependencies {

    }
}
*/


group = "app.example.appiumtest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.seleniumhq.selenium:selenium-java:4.38.0")
    testImplementation("io.appium:java-client:10.0.0")
    testImplementation("org.jeasy:easy-random-core:5.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")

    testImplementation("io.cucumber:cucumber-core:7.16.1")
    testImplementation("io.cucumber:cucumber-java:7.16.1")
    testImplementation("io.cucumber:cucumber-junit:7.16.1")
    testImplementation("org.junit.vintage:junit-vintage-engine:6.0.0")

//    implementation("io.qameta.allure:allure-cucumber6-jvm:2.30.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
