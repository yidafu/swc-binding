plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
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
    implementation("dev.yidafu.swc:swc-binding:0.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // KotlinPoet - 类型安全的 Kotlin 代码生成
    implementation("com.squareup:kotlinpoet:1.15.3")

    // kotlinx-cli - 命令行参数解析
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")

    // YAML 配置解析
    implementation("com.charleskorn.kaml:kaml:0.55.0")
    
    // Kotlin 反射支持（kaml 需要）
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
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
