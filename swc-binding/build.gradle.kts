plugins {
    id("dev.yidafu.library")
    alias(libs.plugins.kotlin.serialization)
}

group = "dev.yidafu.swc"
version = "0.7.0"

dependencies {
    implementation(libs.kotlin.serialization.json)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotest.framework.api)
    testImplementation(libs.kotest.framework.engine)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
}

tasks.test {
    useJUnitPlatform()
}

publishMan {
    name.set("swc binding")
    description.set("swc jvm binding by kotlin")
}