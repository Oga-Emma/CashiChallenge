plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "com.example.cashichallenge"
version = "1.0.0"
application {
    mainClass.set("com.example.cashichallenge.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared) {
        exclude(group = "dev.gitlive")
    }

    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.jvm.firebase.admin)

    testImplementation(libs.ktor.serialization.kotlinx.json)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}
