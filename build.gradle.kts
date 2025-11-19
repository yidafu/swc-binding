plugins {
    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

subprojects {
    repositories {
        maven { setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public") }
        mavenCentral()
        gradlePluginPortal()
    }
}
