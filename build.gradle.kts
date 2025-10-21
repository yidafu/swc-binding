plugins {
    kotlin("jvm") version "1.9.21" apply false
    kotlin("plugin.serialization") version "1.9.21" apply false
}

subprojects {
    repositories {
        maven { setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public") }
        mavenCentral()
        gradlePluginPortal()
    }
}
