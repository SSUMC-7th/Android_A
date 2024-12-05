import org.gradle.internal.classpath.Instrumented.systemProperty
import java.io.FileInputStream
import java.util.Properties

plugins {
    kotlin("plugin.serialization") version "2.0.21"
    kotlin("jvm")
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

listOf(
    "KAKAO_OAUTH_REST_API_KEY",
    "SERVER_URL"
).forEach { key ->
    systemProperty(key, localProperties.getProperty(key))
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization)

    implementation(libs.kotlinx.serialization.json)

    implementation(kotlin("stdlib-jdk8"))

    implementation(libs.java.jwt)

    implementation(libs.logback.classic)
}