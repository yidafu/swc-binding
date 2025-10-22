plugins {
    id("dev.yidafu.library")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

group = "dev.yidafu.swc"
version = "0.7.0"

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    implementation(libs.kotlin.serialization.json)
}

tasks.test {
    useJUnitPlatform()
}

publishMan {
    name.set("swc binding")
    description.set("swc jvm binding by kotlin")
}