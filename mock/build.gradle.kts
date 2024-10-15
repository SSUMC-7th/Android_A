plugins {
    kotlin("plugin.serialization") version "2.0.21"
    kotlin("jvm")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serialization)
    implementation(libs.logback.classic)
    implementation(libs.kotlinx.serialization.json)
    implementation(kotlin("stdlib-jdk8"))
}
