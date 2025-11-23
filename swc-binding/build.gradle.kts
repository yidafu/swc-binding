import java.util.Properties

plugins {
    id("dev.yidafu.library")
    alias(libs.plugins.kotlin.serialization)
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
    alias(libs.plugins.dokka)

    `maven-publish`
    signing
    alias(libs.plugins.nmcp)
}

group = "dev.yidafu.swc"
version = "0.8.0"

// Load local.properties
val localPropertiesFile = project.file("local.properties")
val localProperties = Properties()
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}
kotlin {
    sourceSets {
        val main by getting {
            kotlin.srcDir("src/main/kotlin")
            kotlin.exclude("dev/yidafu/swc/sample/**")
        }
    }
}

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

ktlint {
    filter {
        exclude { element ->
            val path = element.file.path
            path.contains("/generated/dsl/") || path.contains("/generated/ast/")
        }
    }
}

val kotlinSourcesJar = project.tasks.findByName("kotlinSourcesJar") as org.gradle.jvm.tasks.Jar
val dokkaJavadocJar = project.tasks.register<org.gradle.jvm.tasks.Jar>("dokkaJavadocJar") {
    dependsOn("dokkaGenerate")
    from(project.layout.buildDirectory.dir("dokka/html"))
    archiveClassifier.set("javadoc")
}
// Maven Publishing 配置 - POM metadata for all publications
// NMCP plugin automatically uses these publications for uploading to Central Portal
publishing {
    publications {
        create<MavenPublication>("sonatype") {
            // Create empty Javadoc JAR to satisfy Central Portal requirements
            // Dokka HTML is available separately via dokkaHtml task
      
            from(project.components["java"])
            artifact(kotlinSourcesJar)
            artifact(dokkaJavadocJar)
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("swc-binding")
                description.set(
                    "SWC (Speedy Web Compiler) JVM bindings for high-performance TypeScript/JavaScript compilation",
                )
                url.set("https://github.com/yidafu/swc-binding")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("yidafu")
                        name.set("Yidafu")
                        email.set("yidafu90@qq.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/yidafu/swc-binding.git")
                    developerConnection.set("scm:git:ssh://github.com/yidafu/swc-binding.git")
                    url.set("https://github.com/yidafu/swc-binding")
                }
            }
        }
    }
}

// NMCP Plugin 配置 - Modern plugin for Central Portal
// See: https://github.com/GradleUp/nmcp
nmcp {
    // Publish to Central Portal
    // Note: Central Portal does NOT support SNAPSHOT versions
    // All versions must be release versions
    publishAllPublications {
        // Central Portal credentials (Portal user token)
        username.set(
            localProperties.getProperty("centralUsername")
                ?: project.findProperty("centralUsername") as String?
                ?: System.getenv("CENTRAL_USERNAME"),
        )
        password.set(
            localProperties.getProperty("centralPassword")
                ?: project.findProperty("centralPassword") as String?
                ?: System.getenv("CENTRAL_PASSWORD"),
        )

        // Publication type: USER_MANAGED requires manual approval in Portal UI
        // AUTOMATIC would auto-publish, but requires namespace verification
        publicationType.set("USER_MANAGED")
    }
}

// Signing configuration for Maven Central
val signingKey = localProperties.getProperty("signing.key")
    ?: System.getenv("SIGNING_KEY")
val signingPassword = localProperties.getProperty("signing.password")
    ?: System.getenv("SIGNING_PASSWORD")
val signingKeyId = localProperties.getProperty("signing.keyId")
    ?: System.getenv("SIGNING_KEY_ID")

// Always enable signing for Maven Central publication
signing {
    // Use the GPG key from local properties
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey.trim(), signingPassword)
    }
    sign(publishing.publications)
    isRequired = true
}
