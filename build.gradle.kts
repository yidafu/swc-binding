import java.util.Properties

plugins {
    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.nmcp) apply false
}

// Load local.properties
val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

subprojects {
    repositories {
        maven { setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public") }
        mavenCentral()
        gradlePluginPortal()
    }
}
