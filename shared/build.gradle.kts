import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.kotest)
    alias(libs.plugins.ksp)

    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.21"
    id("org.jetbrains.kotlinx.kover") version "0.9.3"
    id("dev.mokkery") version "2.10.2"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    jvm()
    jvmToolchain(17)

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.firebase.common)
            api(libs.kotlin.firebase.firestore)

            api(libs.koin.core)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)

            implementation(libs.napier)

            api(libs.ktor.serialization.kotlinx.json)
            implementation(libs.orandja.either)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
            implementation(libs.kotest.framework.engine)
            implementation(libs.kotest.assertions.core)

            implementation(libs.ktor.client.mock)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotest.runner.junit5)
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    filter {
        isFailOnNoMatchingTests = false
    }
}

android {
    namespace = "com.example.cashichallenge.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
