plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.kotlin.serialization)
    application
    id("dev.yidafu.library")
}

group = "dev.yidafu.swc"
version = "0.1.0"

repositories {
    maven("https://repo.huaweicloud.com/repository/maven/")
    maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
    mavenCentral()
}

dependencies {
    implementation(libs.swc.binding)
    implementation(libs.kotlin.serialization.json)

    // KotlinPoet - 类型安全的 Kotlin 代码生成
    implementation(libs.kotlinpoet)

    // kotlinx-cli - 命令行参数解析
    implementation(libs.kotlinx.cli)

    // YAML 配置解析
    implementation(libs.kaml)

    // Kotlin 反射支持（kaml 需要）
    implementation(libs.kotlin.reflect)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.framework.api)
    testImplementation(libs.kotest.framework.engine)
    testImplementation(libs.kotest.property)
    testImplementation(libs.mockk)
}

application {
    mainClass.set("dev.yidafu.swc.generator.cli.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
